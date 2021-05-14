package com.example.data.repository

import android.annotation.SuppressLint
import android.content.Context
import com.example.data.database.dao.ReviewDao
import com.example.data.database.entity.ReviewEntity
import com.example.data.mappers.ReviewConverterImpl
import com.example.domain.interfaces.ReviewRepository
import com.example.domain.model.Review
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine


class ReviewRepositoryImpl(
    private val reviewDao: ReviewDao,
    private val context: Context,
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : ReviewRepository {

    companion object {
        private const val REVIEW_TABLE = "reviews"
        private const val REVIEW_TABLE_COLUMN_ID = "id"
        private const val REVIEW_TABLE_COLUMN_TEXT = "text"
        private const val REVIEW_TABLE_COLUMN_ID_USER = "id_user"
        private const val REVIEW_TABLE_COLUMN_ID_REST = "id_rest"
        private const val REVIEW_TABLE_COLUMN_RAITING = "raiting"
        private const val REVIEW_TABLE_COLUMN_DATE = "date"
    }

    override suspend fun getListReview(idRestaurant: String): List<Review> {
        var reviewConverterImpl = ReviewConverterImpl()
        var listResult: ArrayList<Review> = ArrayList()
        return suspendCoroutine { continuation ->
            firestore.collection(REVIEW_TABLE).whereEqualTo("id_rest", idRestaurant)
                .get()
                .addOnSuccessListener {
                    for (document in it.documents) {
                        var reviewMap: HashMap<String, String> =
                            document.data as HashMap<String, String>
                        var reviewEntity = ReviewEntity(
                            reviewMap[REVIEW_TABLE_COLUMN_ID].toString(),
                            reviewMap[REVIEW_TABLE_COLUMN_TEXT].toString(),
                            reviewMap[REVIEW_TABLE_COLUMN_ID_USER].toString(),
                            reviewMap[REVIEW_TABLE_COLUMN_DATE].toString(),
//                            SimpleDateFormat(reviewMap[REVIEW_TABLE_COLUMN_ID_REST].toString()).parse("14-02-2018") as Date,
                            reviewMap[REVIEW_TABLE_COLUMN_RAITING]!!.toDouble(),
                            reviewMap[REVIEW_TABLE_COLUMN_ID_REST].toString()
                        )
                        var reviewModel =
                            reviewConverterImpl.dbtoModel(reviewEntity)
                        listResult.add(reviewModel)
                    }
                    continuation.resume(listResult)
                }.addOnFailureListener {
                    //room db
                    continuation.resumeWithException(it)
                }
        }
    }

    override suspend fun getReviewById(id: String): Review {
        return suspendCoroutine { continuation ->
            firestore.collection(REVIEW_TABLE).document(id).get()
                .addOnSuccessListener {
                    var reviewMap: HashMap<String, String?> = it.data as HashMap<String, String?>
                    var reviewEntity = ReviewEntity(
                        reviewMap[REVIEW_TABLE_COLUMN_ID].toString(),
                        reviewMap[REVIEW_TABLE_COLUMN_TEXT].toString(),
                        reviewMap[REVIEW_TABLE_COLUMN_ID_USER].toString(),
                        reviewMap[REVIEW_TABLE_COLUMN_DATE].toString(),
//                            SimpleDateFormat(reviewMap[REVIEW_TABLE_COLUMN_ID_REST].toString()).parse("14-02-2018") as Date,
                        reviewMap[REVIEW_TABLE_COLUMN_RAITING]!!.toDouble(),
                        reviewMap[REVIEW_TABLE_COLUMN_ID_REST].toString()
                    )
                    var reviewConverterImpl = ReviewConverterImpl()
                    var reviewModel =
                        reviewConverterImpl.dbtoModel(reviewEntity)
                    continuation.resume(reviewModel)
                }
        }
    }

    override suspend fun createReviewByUser(review: Review): String {
        var reviewConverterImpl = ReviewConverterImpl()
        var reviewEntity =
            reviewConverterImpl.modeltoDb(review)
        return suspendCoroutine { continuation ->
            val reviewMap = mutableMapOf<String, Any>()
            reviewMap[REVIEW_TABLE_COLUMN_ID] = reviewEntity.id
            reviewMap[REVIEW_TABLE_COLUMN_TEXT] = reviewEntity.text
            reviewMap[REVIEW_TABLE_COLUMN_ID_USER] = reviewEntity.idUser
            reviewMap[REVIEW_TABLE_COLUMN_DATE] = reviewEntity.dateSend
//                            SimpleDateFormat(reviewMap[REVIEW_TABLE_COLUMN_ID_REST].toString()).parse("14-02-2018") as Date,
            reviewMap[REVIEW_TABLE_COLUMN_RAITING] = reviewEntity.raiting.toString()
            reviewMap[REVIEW_TABLE_COLUMN_ID_REST] = reviewEntity.idRest
            firestore.collection(REVIEW_TABLE).document(reviewEntity.id).set(reviewMap)
                .addOnSuccessListener {
                    continuation.resume("Вы успешно создали отзыв")
                }.addOnFailureListener {
                    continuation.resume("Отзыв не был оставлен")
                }
        }
    }
}