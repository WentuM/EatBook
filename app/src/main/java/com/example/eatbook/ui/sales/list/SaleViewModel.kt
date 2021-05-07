package com.example.eatbook.ui.sales.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.SaleInteractor
import com.example.domain.model.Restaurant
import com.example.domain.model.Sale
import kotlinx.coroutines.launch
import java.lang.Exception

class SaleViewModel(
    private val saleInteractor: SaleInteractor
) : ViewModel() {

    private val _sales = MutableLiveData<List<Sale>>()
    fun sales(): LiveData<List<Sale>> = _sales

    fun getAllSales() {
        viewModelScope.launch {
            try {
                _sales.value = saleInteractor.getAllSale()
            } catch (e: Exception) {

            }
        }
    }
}