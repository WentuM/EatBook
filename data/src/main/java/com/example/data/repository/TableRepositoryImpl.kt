package com.example.data.repository

import android.content.Context
import com.example.data.database.dao.TableDao
import com.example.domain.interfaces.TableRepository
import com.example.domain.model.Table
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class TableRepositoryImpl(
    private val tableDao: TableDao,
    private val context: Context,
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : TableRepository {
    override suspend fun getListTable(): List<Table> {
        TODO("Not yet implemented")
    }

    override suspend fun getTableById(id: String): Table {
        TODO("Not yet implemented")
    }
}