package com.example.data.repository

import android.content.Context
import com.example.data.database.dao.BookTableDao
import com.example.data.database.entity.BookTableEntity
import com.example.data.firebase.response.BookTableResponse
import com.example.data.mappers.BookTableConverterImpl
import com.example.domain.interfaces.BookTableRepository
import com.example.domain.model.BookTable
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
        private const val BOOK_TABLE_COLUMN_ID_RESTAURANT = "idRestaurant"
        private const val BOOK_TABLE_COLUMN_NAME_RESTAURANT = "nameRestaurant"
        private const val BOOK_TABLE_COLUMN_NAME_TABLE = "nameTable"
        private const val BOOK_TABLE_COLUMN_IMAGE_TABLE = "imageTable"
    }

    override suspend fun getListByDay(day: String, idTable: String): List<BookTable> {
        val bookTableConverterImpl = BookTableConverterImpl()
        var listResult: List<BookTable> = ArrayList()
        return try {
            val bookTableList: MutableList<BookTableResponse> = firestore.collection(BOOK_TABLE)
                .whereEqualTo(BOOK_TABLE_COLUMN_ID_TABLE, idTable)
                .whereEqualTo(BOOK_TABLE_COLUMN_DAY, day)
                .get().await().toObjects(BookTableResponse::class.java)
            listResult = bookTableList.map { bookTableConverterImpl.fbToModel(it) }
            listResult
        } catch (e: Exception) {
            //b
            return emptyList()
        }
    }

    override suspend fun createBookTable(bookTable: BookTable): String {
        return try {
            val userId = firebaseAuth.currentUser?.uid
            val id = bookTable.idTable + userId + bookTable.day
            val bookTableMap = mutableMapOf<String, Any>()

            bookTableMap[BOOK_TABLE_COLUMN_ID] = id
            bookTableMap[BOOK_TABLE_COLUMN_ID_TABLE] = bookTable.idTable
            bookTableMap[BOOK_TABLE_COLUMN_ID_USER] = userId.toString()
            bookTableMap[BOOK_TABLE_COLUMN_DAY] = bookTable.day
            bookTableMap[BOOK_TABLE_COLUMN_TIME] = bookTable.time
            bookTableMap[BOOK_TABLE_COLUMN_COUNT_HOUR] = bookTable.countHour
            bookTableMap[BOOK_TABLE_COLUMN_ID_RESTAURANT] = bookTable.idRestaurant
            bookTableMap[BOOK_TABLE_COLUMN_NAME_RESTAURANT] = bookTable.nameRestaurant
            bookTableMap[BOOK_TABLE_COLUMN_NAME_TABLE] = bookTable.nameTable
            bookTableMap[BOOK_TABLE_COLUMN_IMAGE_TABLE] = bookTable.imageTable

            firestore
                .collection(BOOK_TABLE)
                .document(id)
                .set(bookTableMap)
                .await()
            "Вы успешно забронировали столик"
        } catch (e: Exception) {
            "Вы не смогли забронировать столик"
        }
    }

    override suspend fun getListMyTable(): List<BookTable> {
        val bookTableConverterImpl = BookTableConverterImpl()
        return try {
            val userId = firebaseAuth.currentUser?.uid
            val listResponse = firestore.collection(BOOK_TABLE)
                .whereEqualTo(BOOK_TABLE_COLUMN_ID_USER, userId).get().await()
                .toObjects(BookTableResponse::class.java)
            listResponse.map { bookTableConverterImpl.fbToModel(it) }
        } catch (e: Exception) {
            emptyList()
            //доступ из бд
        }
    }

    override suspend fun deleteMyTableById(idMyTable: String): String {
        return try {
            firestore.collection(BOOK_TABLE).document(idMyTable).delete().await()
            "Бронирование успешно удалено"
        } catch (e: Exception) {
            "Не удалось удалить бронирование ресторана"
        }
    }
}