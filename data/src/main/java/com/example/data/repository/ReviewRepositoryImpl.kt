package com.example.data.repository

import android.content.Context
import android.util.Log
import com.example.data.database.dao.ReviewDao
import com.example.data.database.dao.UserDao
import com.example.data.database.entity.ReviewEntity
import com.example.data.database.entity.UserEntity
import com.example.data.firebase.response.ReviewResponse
import com.example.data.firebase.response.UserResponse
import com.example.data.mappers.ReviewConverterImpl
import com.example.data.mappers.UserConverterImpl
import com.example.domain.interfaces.ReviewRepository
import com.example.domain.model.Review
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


class ReviewRepositoryImpl(
    private val userDao: UserDao,
    private val reviewDao: ReviewDao,
    private val context: Context,
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : ReviewRepository {

    companion object {
        private const val REVIEW_TABLE = "reviews"
        private const val REVIEW_TABLE_COLUMN_ID = "id"
        private const val REVIEW_TABLE_COLUMN_TEXT = "text"
        private const val REVIEW_TABLE_COLUMN_ID_USER = "idUser"
        private const val REVIEW_TABLE_COLUMN_ID_REST = "idRest"
        private const val REVIEW_TABLE_COLUMN_RAITING = "rating"
        private const val REVIEW_TABLE_COLUMN_DATE = "dateSend"

        private const val USER_TABLE = "users"
        private const val USER_TABLE_COLUMN_PHONE = "phone"
        private const val USER_TABLE_COLUMN_USERNAME = "username"
        private const val USER_TABLE_COLUMN_IMAGE = "image"
    }


    //здесь получить полный список,
    // а в другом методе получить этот готовый список, и по нему достать всех юзеров для отзывов

    override suspend fun getListReview(idRestaurant: String): List<Review> {
        var reviewConverterImpl = ReviewConverterImpl()
        var listResult: ArrayList<Review> = ArrayList()
        return try {
            var reviewList: MutableList<ReviewResponse> = firestore.collection(REVIEW_TABLE).whereEqualTo(REVIEW_TABLE_COLUMN_ID_REST, idRestaurant)
                .get().await().toObjects(ReviewResponse::class.java)
                    for (review in reviewList) {
                        var userEntity: UserEntity = getUserByReview(review.idUser)
                        var reviewEntity = reviewConverterImpl.fbtoDb(reviewResponse = review)
                        var reviewModel = reviewConverterImpl.dbtoModel(reviewEntity, userEntity)

                        listResult.add(reviewModel)
                    }
            listResult
        } catch (e: Exception) {
            //b
            return emptyList()
        }
    }

    override suspend fun createReviewByUser(review: Review): String {
        var userId = firebaseAuth.currentUser?.uid
        var reviewConverterImpl = ReviewConverterImpl()
        var reviewEntity: ReviewEntity =
            reviewConverterImpl.modeltoDb(review)
        return suspendCoroutine { continuation ->
            val reviewMap = mutableMapOf<String, Any>()
            reviewMap[REVIEW_TABLE_COLUMN_ID] = reviewEntity.id
            reviewMap[REVIEW_TABLE_COLUMN_TEXT] = reviewEntity.text
            reviewMap[REVIEW_TABLE_COLUMN_ID_USER] = userId.toString()
            reviewMap[REVIEW_TABLE_COLUMN_DATE] = reviewEntity.dateSend
            reviewMap[REVIEW_TABLE_COLUMN_RAITING] = reviewEntity.rating
            reviewMap[REVIEW_TABLE_COLUMN_ID_REST] = reviewEntity.idRest
            firestore.collection(REVIEW_TABLE).document(reviewEntity.id).set(reviewMap)
                .addOnSuccessListener {
                    continuation.resume("Вы успешно создали отзыв")
                }.addOnFailureListener {
                    continuation.resume("Отзыв не был оставлен")
                }
        }
    }

    private suspend fun getUserByReview(userId: String): UserEntity {
        var userConverterImpl = UserConverterImpl()
        return try {
            var userEntity = UserEntity()
            var userResponse: UserResponse? = firestore.collection(USER_TABLE).document(userId).get().await().toObject(UserResponse::class.java)

            if (userResponse != null) {
                userEntity = userConverterImpl.fbtoDb(userResponse)
            }
            userEntity
        } catch (e: Exception) {
            userDao.getUserById(userId)
        }
    }
}