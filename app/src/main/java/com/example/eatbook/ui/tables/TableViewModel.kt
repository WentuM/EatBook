package com.example.eatbook.ui.tables

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.interactor.TableInteractor
import com.example.domain.model.Sale
import com.example.domain.model.Table
import kotlinx.coroutines.launch
import java.lang.Exception

class TableViewModel(
    private val tableInteractor: TableInteractor
): ViewModel() {

    private val _tables = MutableLiveData<List<Table>>()
    fun tables(): LiveData<List<Table>> = _tables

    fun getAllSales() {
        viewModelScope.launch {
            try {
                _tables.value = tableInteractor.getAllTable()
            } catch (e: Exception) {

            }
        }
    }
}