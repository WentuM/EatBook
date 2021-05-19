package com.example.eatbook.ui.tables.booking

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.interactor.BookTableInteractor
import com.example.domain.interactor.TableInteractor
import com.example.domain.model.BookTable
import com.example.domain.model.Restaurant
import com.example.domain.model.Review
import com.example.eatbook.ui.reviews.list.model.ReviewList
import com.example.eatbook.ui.tables.booking.list.model.BookTableItemModel
import com.example.eatbook.ui.tables.booking.list.model.Hour
import kotlinx.coroutines.launch
import java.lang.Exception

class BookTableViewModel(
    private val bookTableInteractor: BookTableInteractor
): ViewModel() {

    private val _bookTable = MutableLiveData<List<Hour>>()
    fun bookTable(): LiveData<List<Hour>> = _bookTable
    private val _createBookTable = MutableLiveData<String>()
    fun createBookTable(): LiveData<String> = _createBookTable

    fun createNewBookTable(bookTableItemModel: BookTableItemModel) {
        viewModelScope.launch {
            try {
                _createBookTable.value = bookTableInteractor.createBookTable(
                    mapBookTableItemModelToBookTable(bookTableItemModel))
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
            BookTable(idTable, day, time, countHour)
        }
    }

//    private fun mapBookTableToBookTableItemModel(bookTable: BookTable): BookTableItemModel {
//        return with(bookTable) {
//            BookTableItemModel(idTable, day, time)
//        }
//    }

    private fun mapBookTableToHour(bookTable: BookTable): Hour {
        return with(bookTable) {
            Hour(time, true, countHour)
        }
    }
}