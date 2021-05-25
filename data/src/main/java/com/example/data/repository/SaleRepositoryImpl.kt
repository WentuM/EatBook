package com.example.data.repository

import android.content.Context
import com.example.data.database.dao.SaleDao
import com.example.data.database.entity.RestaurantEntity
import com.example.data.database.entity.SaleEntity
import com.example.data.firebase.response.SaleResponse
import com.example.data.mappers.RestaurantConverterImpl
import com.example.data.mappers.SaleConverterImpl
import com.example.domain.interfaces.SaleRepository
import com.example.domain.model.Restaurant
import com.example.domain.model.Sale
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class SaleRepositoryImpl(
    private val saleDao: SaleDao,
    private val context: Context,
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : SaleRepository {

    companion object {
        private const val SALES_TABLE = "sales"
    }

    override suspend fun getListSales(): List<Sale> {
        val saleConverterImpl = SaleConverterImpl()
        var listResult: List<Sale> = emptyList()

        return try {
            val listResponse: List<SaleResponse> =
                firestore.collection(SALES_TABLE).get().await()
                    .toObjects(SaleResponse::class.java)
            listResult = listResponse.map { saleConverterImpl.fbtoModel(it) }
            listResult
        } catch (e: Exception) {
            emptyList()
        }
    }

    override suspend fun getSalesById(id: String): Sale {
        val saleConverterImpl = SaleConverterImpl()
        var saleResponse: SaleResponse? = SaleResponse()
        var resultSale: Sale = Sale()
        return try {
            saleResponse = firestore.collection(SALES_TABLE).document(id).get().await()
                .toObject(SaleResponse::class.java)
            if (saleResponse != null) {
                resultSale = saleConverterImpl.fbtoModel(saleResponse)
            }
            resultSale
        } catch (e: Exception) {
            //
            resultSale
        }
    }
}