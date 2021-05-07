package com.example.domain.interfaces

import com.example.domain.model.Sale

interface SaleRepository {

    suspend fun getListSales(): List<Sale>

    suspend fun getSalesById(id: String): Sale
}