package com.example.domain

import com.example.domain.interfaces.SaleRepository
import com.example.domain.model.Sale
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class SaleInteractor(
    private val saleRepository: SaleRepository,
    private val context: CoroutineContext
) {
    suspend fun getSaleById(id: String): Sale =
        withContext(context) {
            saleRepository.getSalesById(id)
        }

    suspend fun getAllSale(): ArrayList<Sale> =
        withContext(context) {
            saleRepository.getListSales() as ArrayList<Sale>
        }
}