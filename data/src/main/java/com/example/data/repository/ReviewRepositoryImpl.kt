package com.example.data.repository

import com.example.data.database.dao.ReviewDao
import com.example.data.database.entity.ReviewEntity
import com.example.data.firebase.response.ReviewResponse
import com.example.data.firebase.response.UserResponse
import com.example.data.mappers.ReviewConverter
import com.example.domain.interfaces.ReviewRepository
import com.example.domain.model.Review
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await


class ReviewRepositoryImpl(
    private val reviewDao: ReviewDao,
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
    private val reviewConverterImpl: ReviewConverter
) : ReviewRepository {

    companion object {
        private const val USER_TABLE = "users"
        private const val REVIEW_TABLE = "reviews"
        private const val REVIEW_TABLE_COLUMN_ID = "id"
        private const val REVIEW_TABLE_COLUMN_TEXT = "text"
        private const val REVIEW_TABLE_COLUMN_ID_USER = "idUser"
        private const val REVIEW_TABLE_COLUMN_ID_REST = "idRest"
        private const val REVIEW_TABLE_COLUMN_RATING = "rating"
        private const val REVIEW_TABLE_COLUMN_DATE = "dateSend"
        private const val REVIEW_TABLE_COLUMN_NAME_USER = "nameUser"
        private const val REVIEW_TABLE_COLUMN_IMAGE_USER = "imageUser"
    }

    override suspend fun getListReview(idRestaurant: String): List<Review> {
        var listResult: List<Review>
        return try {
            val reviewList: MutableList<ReviewResponse> = firestore.collection(REVIEW_TABLE)
                .whereEqualTo(REVIEW_TABLE_COLUMN_ID_REST, idRestaurant)
                .get().await().toObjects(ReviewResponse::class.java)
            listResult = reviewList.map { reviewConverterImpl.fbtoModel(it) }
            listResult
        } catch (e: Exception) {
            listResult =
                reviewDao.getAllFullReview().map { reviewConverterImpl.reviewFullToModel(it) }
            listResult
        }
    }

    override suspend fun createReviewByUser(review: Review): String {
        return try {
            val userId = firebaseAuth.currentUser?.uid.toString()
//            val userResponse = firestore.collection(USER_TABLE).document(userId).get().await()
//                .toObject(UserResponse::class.java)
            val reviewMap = mutableMapOf<String, Any>()
            var reviewEntity = ReviewEntity()
            with(review) {
//                if (userResponse != null) {
                    reviewEntity = ReviewEntity(id, text, userId, dateSend, rating, idRest)
                    reviewMap[REVIEW_TABLE_COLUMN_ID] = id
                    reviewMap[REVIEW_TABLE_COLUMN_TEXT] = text
                    reviewMap[REVIEW_TABLE_COLUMN_ID_USER] = userId
                    reviewMap[REVIEW_TABLE_COLUMN_DATE] = dateSend
                    reviewMap[REVIEW_TABLE_COLUMN_RATING] = rating
                    reviewMap[REVIEW_TABLE_COLUMN_ID_REST] = idRest
                    reviewMap[REVIEW_TABLE_COLUMN_NAME_USER] = nameUser
                    reviewMap[REVIEW_TABLE_COLUMN_IMAGE_USER] = imageUser
//                }
            }

            firestore
                .collection(REVIEW_TABLE)
                .document(review.id)
                .set(reviewMap)
                .await()

            reviewDao.insert(reviewEntity)

            "Вы успешно создали отзыв"
        } catch (e: Exception) {
            "Отзыв не был оставлен"
        }
    }
}