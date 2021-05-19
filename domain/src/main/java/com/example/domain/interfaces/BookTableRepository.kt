package com.example.domain.interfaces

import com.example.domain.model.BookTable

interface BookTableRepository {

    suspend fun getListByDay(day: String, idTable: String): List<BookTable>

    suspend fun createBookTable(bookTable: BookTable): String
}