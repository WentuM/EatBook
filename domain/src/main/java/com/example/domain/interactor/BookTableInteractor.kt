package com.example.domain.interactor

import com.example.domain.interfaces.BookTableRepository
import com.example.domain.interfaces.TableRepository
import com.example.domain.model.BookTable
import com.example.domain.model.Table
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class BookTableInteractor(
    private val bookTableRepository: BookTableRepository,
    private val tableRepository: TableRepository,
    private val context: CoroutineContext
) {

    suspend fun getAllTableByDay(day: String, idTable: String): ArrayList<BookTable> =
        withContext(context) {
            bookTableRepository.getListByDay(day, idTable) as ArrayList<BookTable>
        }

    suspend fun createBookTable(bookTable: BookTable): String =
        withContext(context) {
            bookTableRepository.createBookTable(bookTable)
        }

    suspend fun getTableForBookTable(idTable: String): Table =
        withContext(context) {
            tableRepository.getTableById(idTable)
        }
}