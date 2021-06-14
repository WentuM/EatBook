package com.example.eatbook.ui.tables.booking

import android.util.Log
import android.view.View
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
import kotlinx.android.synthetic.main.fragment_book_rest.view.*
import kotlinx.coroutines.launch

class BookTableViewModel(
    private val bookTableInteractor: BookTableInteractor
) : ViewModel() {

    private val _bookTable = MutableLiveData<List<Hour>>()
    fun bookTable(): LiveData<List<Hour>> = _bookTable

    private val _createBookTable = MutableLiveData<String>()
    fun createBookTable(): LiveData<String> = _createBookTable

    private val _getTable = MutableLiveData<TableItemModel>()
    fun getTable(): LiveData<TableItemModel> = _getTable

    fun getTableById(idTable: String, view: View) {
        viewModelScope.launch {
            try {
                view.progress_list.visibility = View.VISIBLE
                _getTable.value =
                    mapTableToTableItemModel(bookTableInteractor.getTableForBookTable(idTable))
            } catch (e: Exception) {

            } finally {
                view.progress_list.visibility = View.GONE
            }
        }
    }

    fun createNewBookTable(bookTableItemModel: BookTableItemModel, view: View) {
        viewModelScope.launch {
            try {
                view.progress_list.visibility = View.VISIBLE
                _createBookTable.value = bookTableInteractor.createBookTable(
                    mapBookTableItemModelToBookTable(bookTableItemModel)
                )
            } catch (e: Exception) {

            } finally {
                view.progress_list.visibility = View.GONE
            }
        }
    }

    fun getBookTableByDay(day: String, idTable: String, view: View) {
        viewModelScope.launch {
            try {
                view.progress_list.visibility = View.VISIBLE
                _bookTable.value = bookTableInteractor.getAllTableByDay(day, idTable)
                    .map { mapBookTableToHour(it) }
            } catch (e: Exception) {
                Log.d("qweEXce", e.toString())
            } finally {
                view.progress_list.visibility = View.GONE
            }
        }
    }

    fun getCurrentUser(): Boolean = bookTableInteractor.loggedUser()

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
                nameRestaurant,
                countPlaces
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