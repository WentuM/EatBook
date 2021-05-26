package com.example.data.repository

import com.example.data.database.dao.TableDao
import com.example.data.firebase.response.TableResponse
import com.example.data.mappers.TableConverter
import com.example.domain.interfaces.TableRepository
import com.example.domain.model.Table
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class TableRepositoryImpl(
    private val tableDao: TableDao,
    private val firestore: FirebaseFirestore,
    private val tableConverterImpl: TableConverter

) : TableRepository {

    companion object {
        private const val TABLE_TABLE = "tables"
        private const val TABLE_COLUMN_ID_REST = "idRest"
    }

    override suspend fun getListTable(idRestaurant: String): List<Table> {
        var listResult: ArrayList<Table> = ArrayList()
        return try {
            val tableList: MutableList<TableResponse> =
                firestore.collection(TABLE_TABLE).whereEqualTo(
                    TABLE_COLUMN_ID_REST, idRestaurant
                )
                    .get().await().toObjects(TableResponse::class.java)
            for (table in tableList) {
                val tableEntity = tableConverterImpl.fbtoDb(table)
                val tableModel = tableConverterImpl.dbtoModel(tableEntity)

                listResult.add(tableModel)
            }
            listResult
        } catch (e: Exception) {
            tableDao.getAllTables().map { tableConverterImpl.dbtoModel(it) }
        }
    }

    override suspend fun getTableById(id: String): Table {
        var result: Table = Table()
        return try {
            val tableResponse =
                firestore.collection(TABLE_TABLE).document(id).get().await()
                    .toObject(TableResponse::class.java)
            if (tableResponse != null) {
                val tableEntity = tableConverterImpl.fbtoDb(tableResponse)
                result = tableConverterImpl.dbtoModel(tableEntity)
            }
            result
        } catch (e: Exception) {
            val tableEntity = tableConverterImpl.dbtoModel(tableDao.getTableById(id))
            tableEntity
        }
    }
}