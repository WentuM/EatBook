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
        private const val REVIEW_TABLE_COLUMN_NAME_USER = "nameUser"
        private const val REVIEW_TABLE_COLUMN_IMAGE_USER = "imageUser"
    }

    override suspend fun getListReview(idRestaurant: String): List<Review> {
        val reviewConverterImpl = ReviewConverterImpl()
        var listResult: List<Review> = emptyList<Review>()
        return try {
            val reviewList: MutableList<ReviewResponse> = firestore.collection(REVIEW_TABLE)
                .whereEqualTo(REVIEW_TABLE_COLUMN_ID_REST, idRestaurant)
                .get().await().toObjects(ReviewResponse::class.java)
            listResult = reviewList.map { reviewConverterImpl.fbtoModel(it) }
//            for (review in reviewList) {
//                val userEntity: UserEntity = getUserByReview(review.idUser)
//                val reviewEntity = reviewConverterImpl.fbtoDb(reviewResponse = review)
//                val reviewModel = reviewConverterImpl.fbtoModel(revi, userEntity)
//
//                listResult.add(reviewModel)
//            }
            listResult
        } catch (e: Exception) {
            //b
            return emptyList()
        }
    }

    override suspend fun createReviewByUser(review: Review): String {
        return try {
            val userId = firebaseAuth.currentUser?.uid
            val reviewMap = mutableMapOf<String, Any>()

            reviewMap[REVIEW_TABLE_COLUMN_ID] = review.id
            reviewMap[REVIEW_TABLE_COLUMN_TEXT] = review.text
            reviewMap[REVIEW_TABLE_COLUMN_ID_USER] = userId.toString()
            reviewMap[REVIEW_TABLE_COLUMN_DATE] = review.dateSend
            reviewMap[REVIEW_TABLE_COLUMN_RAITING] = review.rating
            reviewMap[REVIEW_TABLE_COLUMN_ID_REST] = review.idRest
            reviewMap[REVIEW_TABLE_COLUMN_NAME_USER] = review.nameUser
            reviewMap[REVIEW_TABLE_COLUMN_IMAGE_USER] = review.imageUser


            firestore
                .collection(REVIEW_TABLE)
                .document(review.id)
                .set(reviewMap)
                .await()

            "Вы успешно создали отзыв"
        } catch (e: Exception) {
            "Отзыв не был оставлен"
        }
    }
}