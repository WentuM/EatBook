package com.example.eatbook.ui.tables.booking

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.interactor.BookTableInteractor
import com.example.domain.model.BookTable
import com.example.domain.model.Table
import com.example.eatbook.ui.tables.booking.list.model.BookTableItemModel
import com.example.eatbook.ui.tables.booking.list.model.Hour
import com.example.eatbook.ui.tables.booking.list.model.TableItemModel
import kotlinx.coroutines.launch
import java.lang.Exception

class BookTableViewModel(
    private val bookTableInteractor: BookTableInteractor
) : ViewModel() {

    private val _bookTable = MutableLiveData<List<Hour>>()
    fun bookTable(): LiveData<List<Hour>> = _bookTable

    private val _createBookTable = MutableLiveData<String>()
    fun createBookTable(): LiveData<String> = _createBookTable

    private val _getTable = MutableLiveData<TableItemModel>()
    fun getTable(): LiveData<TableItemModel> = _getTable

    fun getTableById(idTable: String) {
        viewModelScope.launch {
            try {
                _getTable.value =
                    mapTableToTableItemModel(bookTableInteractor.getTableForBookTable(idTable))
            } catch (e: Exception) {

            }
        }
    }

    fun createNewBookTable(bookTableItemModel: BookTableItemModel) {
        viewModelScope.launch {
            try {
                _createBookTable.value = bookTableInteractor.createBookTable(
                    mapBookTableItemModelToBookTable(bookTableItemModel)
                )
            } catch (e: Exception) {

            }
        }
    }

    fun getBookTableByDay(day: String, idTable: String) {
        viewModelScope.launch {
            try {
                _bookTable.value = bookTableInteractor.getAllTableByDay(day, idTable)
                    .map { mapBookTableToHour(it) }
            } catch (e: Exception) {

            }
        }
    }

    private fun mapBookTableItemModelToBookTable(bookTableItemModel: BookTableItemModel): BookTable {
        return with(bookTableItemModel) {
            BookTable(
                id,
                idTable,
                day,
                time,
                countHour,
                imageTable,
                nameTable,
                idRestaurant,
                nameRestaurant
            )
        }
    }

    private fun mapTableToTableItemModel(table: Table): TableItemModel {
        return with(table) {
            TableItemModel(
                id, title, countPlaces, image, idRest, nameRestaurant
            )
        }
    }

    private fun mapBookTableToHour(bookTable: BookTable): Hour {
        return with(bookTable) {
            Hour(time, true, countHour)
        }
    }
}