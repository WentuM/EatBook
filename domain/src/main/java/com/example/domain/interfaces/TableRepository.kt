package com.example.domain.interfaces

import com.example.domain.model.Sale
import com.example.domain.model.Table

interface TableRepository {

    suspend fun getListTable(): List<Table>

    suspend fun getTableById(id: String): Table
}