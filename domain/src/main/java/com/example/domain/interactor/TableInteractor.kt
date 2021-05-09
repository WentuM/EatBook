package com.example.domain.interactor

import com.example.domain.interfaces.TableRepository
import com.example.domain.model.Sale
import com.example.domain.model.Table
import com.example.domain.model.User
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class TableInteractor(
    private val tableRepository: TableRepository,
    private val context: CoroutineContext
) {

    suspend fun getAllTable(): ArrayList<Table> =
        withContext(context) {
            tableRepository.getListTable() as ArrayList<Table>
        }
}