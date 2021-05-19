package com.example.data.repository

import android.content.Context
import com.example.data.database.dao.TableDao
import com.example.data.database.entity.UserEntity
import com.example.data.firebase.response.ReviewResponse
import com.example.data.firebase.response.TableResponse
import com.example.data.mappers.TableConverterImpl
import com.example.domain.interfaces.TableRepository
import com.example.domain.model.Review
import com.example.domain.model.Table
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import java.lang.Exception

class TableRepositoryImpl(
    private val tableDao: TableDao,
    private val context: Context,
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore

) : TableRepository {

    companion object {
        private const val TABLE_TABLE = "tables"
        private const val TABLE_COLUMN_ID_REST = "idRest"
    }

    override suspend fun getListTable(idRestaurant: String): List<Table> {
        var tableConverterImpl = TableConverterImpl()
        var listResult: ArrayList<Table> = ArrayList()
        return try {
            var tableList: MutableList<TableResponse> = firestore.collection(TABLE_TABLE).whereEqualTo(
                TABLE_COLUMN_ID_REST, idRestaurant)
                .get().await().toObjects(TableResponse::class.java)
            for (table in tableList) {
                var tableEntity = tableConverterImpl.fbtoDb(table)
                var tableModel = tableConverterImpl.dbtoModel(tableEntity)

                listResult.add(tableModel)
            }
            listResult
        } catch (e: Exception) {
            emptyList<Table>()
        }
    }

    override suspend fun getTableById(id: String): Table {
        TODO("Not yet implemented")
    }
}