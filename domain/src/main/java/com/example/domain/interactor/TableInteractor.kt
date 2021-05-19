package com.example.domain.interactor

import com.example.domain.interfaces.TableRepository
import com.example.domain.model.Table
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class TableInteractor(
    private val tableRepository: TableRepository,
    private val context: CoroutineContext
) {

    suspend fun getAllTableByIdRest(idRestaurant: String): ArrayList<Table> =
        withContext(context) {
            tableRepository.getListTable(idRestaurant) as ArrayList<Table>
        }
}