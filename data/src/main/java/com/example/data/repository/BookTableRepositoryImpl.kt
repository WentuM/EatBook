package com.example.data.repository

import android.content.Context
import android.util.Log
import com.example.data.database.dao.BookTableDao
import com.example.data.database.entity.BookTableEntity
import com.example.data.firebase.response.BookTableResponse
import com.example.data.mappers.BookTableConverter
import com.example.data.mappers.BookTableConverterImpl
import com.example.domain.interfaces.BookTableRepository
import com.example.domain.model.BookTable
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class BookTableRepositoryImpl(
    private val bookTableDao: BookTableDao,
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
    private val bookTableConverterImpl: BookTableConverter
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
        private const val BOOK_TABLE_COLUMN_COUNT_PLACES = "countPlaces"
    }

    override suspend fun getListByDay(day: String, idTable: String): List<BookTable> {
        var listResult: List<BookTable>
        return try {
            val bookTableList: MutableList<BookTableResponse> = firestore.collection(BOOK_TABLE)
                .whereEqualTo(BOOK_TABLE_COLUMN_ID_TABLE, idTable)
                .whereEqualTo(BOOK_TABLE_COLUMN_DAY, day)
                .get().await().toObjects(BookTableResponse::class.java)
            listResult = bookTableList.map { bookTableConverterImpl.fbToModel(it) }
            listResult
        } catch (e: Exception) {
            listResult = bookTableDao.getAllTablesByDayAndTable(day, idTable)
                .map { bookTableConverterImpl.fullBookTableToModel(it) }
            listResult
        }
    }

    override suspend fun createBookTable(bookTable: BookTable): String {
        return try {
            val userId = firebaseAuth.currentUser?.uid.toString()
            val id = bookTable.idTable + userId + bookTable.day + bookTable.time
            val bookTableMap = mutableMapOf<String, Any>()
            var bookTableEntity: BookTableEntity
            bookTableMap[BOOK_TABLE_COLUMN_ID] = id
            bookTableMap[BOOK_TABLE_COLUMN_ID_USER] = userId

            with(bookTable) {
                bookTableEntity = BookTableEntity(id, userId, idTable, day, time, countHour)

                bookTableMap[BOOK_TABLE_COLUMN_ID_TABLE] = idTable
                bookTableMap[BOOK_TABLE_COLUMN_DAY] = day
                bookTableMap[BOOK_TABLE_COLUMN_TIME] = time
                bookTableMap[BOOK_TABLE_COLUMN_COUNT_HOUR] = countHour
                bookTableMap[BOOK_TABLE_COLUMN_ID_RESTAURANT] = idRestaurant
                bookTableMap[BOOK_TABLE_COLUMN_NAME_RESTAURANT] = nameRestaurant
                bookTableMap[BOOK_TABLE_COLUMN_NAME_TABLE] = nameTable
                bookTableMap[BOOK_TABLE_COLUMN_IMAGE_TABLE] = imageTable
                bookTableMap[BOOK_TABLE_COLUMN_COUNT_PLACES] = countPlaces
            }

            firestore
                .collection(BOOK_TABLE)
                .document(id)
                .set(bookTableMap)
                .await()

            bookTableDao.insert(bookTableEntity)

            "Вы успешно забронировали столик"
        } catch (e: Exception) {
            "Вы не смогли забронировать столик"
        }
    }

    override suspend fun getListMyTable(): List<BookTable> {
        val userId = firebaseAuth.currentUser?.uid.toString()
        return try {
            val listResponse = firestore.collection(BOOK_TABLE)
                .whereEqualTo(BOOK_TABLE_COLUMN_ID_USER, userId).get().await()
                .toObjects(BookTableResponse::class.java)
            listResponse.map { bookTableConverterImpl.fbToModel(it) }
        } catch (e: Exception) {
            val listResult = bookTableDao.getAllFullBookTable(userId)
                .map { bookTableConverterImpl.fullBookTableToModel(it) }
            listResult
        }
    }

    override suspend fun deleteMyTableById(idMyTable: String): String {
        return try {
            firestore.collection(BOOK_TABLE).document(idMyTable).delete().await()
            val bookTable = bookTableDao.getBookTableById(idMyTable)
            bookTableDao.delete(bookTable)
            "Бронирование успешно удалено"
        } catch (e: Exception) {
            "Не удалось удалить бронирование ресторана"
        }
    }
}