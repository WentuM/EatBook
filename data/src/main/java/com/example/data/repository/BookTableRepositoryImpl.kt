package com.example.data.repository

import android.content.Context
import com.example.data.database.dao.BookTableDao
import com.example.data.database.dao.RestaurantDao
import com.example.data.database.entity.BookTableEntity
import com.example.data.database.entity.UserEntity
import com.example.data.firebase.response.BookTableResponse
import com.example.data.firebase.response.ReviewResponse
import com.example.data.mappers.BookTableConverter
import com.example.data.mappers.BookTableConverterImpl
import com.example.data.mappers.ReviewConverterImpl
import com.example.domain.interfaces.BookTableRepository
import com.example.domain.interfaces.RestaurantRepository
import com.example.domain.model.BookTable
import com.example.domain.model.Review
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class BookTableRepositoryImpl(
    private val bookTableDao: BookTableDao,
    private val context: Context,
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : BookTableRepository {

    companion object {
        private const val BOOK_TABLE = "book_table"
        private const val BOOK_TABLE_COLUMN_DAY = "day"
        private const val BOOK_TABLE_COLUMN_ID_TABLE = "idTable"
        private const val BOOK_TABLE_COLUMN_ID_USER = "idUser"
        private const val BOOK_TABLE_COLUMN_ID = "id"
        private const val BOOK_TABLE_COLUMN_TIME = "time"
        private const val BOOK_TABLE_COLUMN_COUNT_HOUR = "countHour"
    }

    override suspend fun getListByDay(day: String, idTable: String): List<BookTable> {
        val bookTableConverterImpl = BookTableConverterImpl()
        var listResult: List<BookTable> = ArrayList()
        return try {
            var reviewList: MutableList<BookTableResponse> = firestore.collection(BOOK_TABLE)
                .whereEqualTo(BOOK_TABLE_COLUMN_ID_TABLE, idTable)
                .whereEqualTo(BOOK_TABLE_COLUMN_DAY, day)
                .get().await().toObjects(BookTableResponse::class.java)
            var listEntity: List<BookTableEntity> =
                reviewList.map { bookTableConverterImpl.fbToDb(it) }
            listResult = listEntity.map { bookTableConverterImpl.dbToModel(it) }
//            for (review in reviewList) {
//                var userEntity: UserEntity = getUserByReview(review.idUser)
//                var reviewEntity = reviewConverterImpl.fbtoDb(reviewResponse = review)
//                var reviewModel = reviewConverterImpl.dbtoModel(reviewEntity, userEntity)
//
//                listResult.add(reviewModel)
//            }
            listResult
        } catch (e: Exception) {
            //b
            return emptyList()
        }
    }

    override suspend fun createBookTable(bookTable: BookTable): String {
        return try {
            var userId = firebaseAuth.currentUser?.uid
            var id = bookTable.idTable + userId
            val bookTableMap = mutableMapOf<String, Any>()
            bookTableMap[BOOK_TABLE_COLUMN_ID] = id
            bookTableMap[BOOK_TABLE_COLUMN_ID_TABLE] = bookTable.idTable
            bookTableMap[BOOK_TABLE_COLUMN_ID_USER] = userId.toString()
            bookTableMap[BOOK_TABLE_COLUMN_DAY] = bookTable.day
            bookTableMap[BOOK_TABLE_COLUMN_TIME] = bookTable.time
            bookTableMap[BOOK_TABLE_COLUMN_COUNT_HOUR] = bookTable.countHour
            val data = firestore
                .collection(BOOK_TABLE)
                .document(id)
                .set(bookTableMap)
                .await()
            "Вы успешно забронировали столик"
        } catch (e: Exception) {
            "$e"
        }
    }
}