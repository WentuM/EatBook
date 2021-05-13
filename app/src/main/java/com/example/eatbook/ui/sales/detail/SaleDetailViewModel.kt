package com.example.eatbook.ui.sales.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.interactor.SaleInteractor
import com.example.domain.model.Restaurant
import com.example.domain.model.Sale
import kotlinx.coroutines.launch

class SaleDetailViewModel(
    private val saleInteractor: SaleInteractor
): ViewModel() {

    private val _getSale: MutableLiveData<Sale> = MutableLiveData()


    fun getSale(): LiveData<Sale> = _getSale


    fun getSaleById(idSale: String) {
        viewModelScope.launch {
            try {
                _getSale.value = saleInteractor.getSaleById(idSale)
            } catch (e: Exception) {

            }
        }
    }
}