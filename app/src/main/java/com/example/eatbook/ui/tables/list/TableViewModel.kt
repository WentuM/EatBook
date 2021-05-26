package com.example.eatbook.ui.tables.list

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.interactor.TableInteractor
import com.example.domain.model.Table
import com.example.eatbook.ui.tables.list.model.TableListModel
import kotlinx.android.synthetic.main.progress_bar.view.*
import kotlinx.coroutines.launch

class TableViewModel(
    private val tableInteractor: TableInteractor
) : ViewModel() {

    private val _tables = MutableLiveData<List<TableListModel>>()
    fun tables(): LiveData<List<TableListModel>> = _tables

    fun getAllTables(idRestaurant: String, view: View) {
        viewModelScope.launch {
            try {
                view.progress_list.visibility = View.VISIBLE
                _tables.value = tableInteractor.getAllTableByIdRest(idRestaurant)
                    .map { mapTableToTableListModel(it) }
            } catch (e: Exception) {

            } finally {
                view.progress_list.visibility = View.GONE
            }
        }
    }

    private fun mapTableToTableListModel(table: Table): TableListModel {
        return with(table) {
            TableListModel(
                id, title, countPlaces, image, idRest, nameRestaurant
            )
        }
    }
}