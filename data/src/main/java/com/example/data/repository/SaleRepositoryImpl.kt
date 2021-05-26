package com.example.data.repository

import android.content.Context
import com.example.data.database.dao.SaleDao
import com.example.data.firebase.response.SaleResponse
import com.example.data.mappers.SaleConverter
import com.example.domain.interfaces.SaleRepository
import com.example.domain.model.Sale
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class SaleRepositoryImpl(
    private val saleDao: SaleDao,
    private val firestore: FirebaseFirestore,
    private val saleConverterImpl: SaleConverter
) : SaleRepository {

    companion object {
        private const val SALES_TABLE = "sales"
    }

    override suspend fun getListSales(): List<Sale> {
        val listResult: List<Sale>

        return try {
            val listResponse: List<SaleResponse> =
                firestore.collection(SALES_TABLE).get().await()
                    .toObjects(SaleResponse::class.java)
            val listEntity = listResponse.map { saleConverterImpl.fbtoDb(it) }
            listResult = listResponse.map { saleConverterImpl.fbtoModel(it) }
            saleDao.insertList(listEntity)
            listResult
        } catch (e: Exception) {
            saleDao.getAllSales().map { saleConverterImpl.dbtoModel(it) }
        }
    }

    override suspend fun getSalesById(id: String): Sale {
        val saleResponse: SaleResponse?
        var resultSale: Sale = Sale()
        return try {
            saleResponse = firestore.collection(SALES_TABLE).document(id).get().await()
                .toObject(SaleResponse::class.java)
            if (saleResponse != null) {
                resultSale = saleConverterImpl.fbtoModel(saleResponse)
            }
            resultSale
        } catch (e: Exception) {
            resultSale = saleConverterImpl.dbtoModel(saleDao.getSaleById(id))
            resultSale
        }
    }
}