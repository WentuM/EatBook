package com.example.data.repository

import android.content.Context
import com.example.data.database.dao.SaleDao
import com.example.domain.interfaces.SaleRepository
import com.example.domain.model.Restaurant
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class SaleRepositoryImpl(
    private val saleDao: SaleDao,
    private val context: Context,
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : SaleRepository {

    override suspend fun getListSales(): List<Restaurant> {
        TODO("Not yet implemented")
    }

    override suspend fun getSalesById(id: String): Restaurant {
        TODO("Not yet implemented")
    }
}