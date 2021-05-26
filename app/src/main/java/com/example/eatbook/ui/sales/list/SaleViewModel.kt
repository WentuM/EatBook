package com.example.eatbook.ui.sales.list

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.interactor.SaleInteractor
import com.example.domain.model.Sale
import com.example.eatbook.ui.sales.detail.model.SaleItemModel
import com.example.eatbook.ui.sales.list.model.SaleListModel
import kotlinx.android.synthetic.main.progress_bar.view.*
import kotlinx.coroutines.launch
import java.lang.Exception

class SaleViewModel(
    private val saleInteractor: SaleInteractor
) : ViewModel() {

    private val _sales = MutableLiveData<List<SaleListModel>>()
    fun sales(): LiveData<List<SaleListModel>> = _sales

    fun getAllSales(view: View) {
        viewModelScope.launch {
            try {
                view.progress_list.visibility = View.VISIBLE
                _sales.value = saleInteractor.getAllSale().map { mapSaleToSaleListModel(it) }
            } catch (e: Exception) {

            } finally {
                view.progress_list.visibility = View.GONE
            }
        }
    }

    private fun mapSaleToSaleListModel(sale: Sale): SaleListModel {
        return with(sale) {
            SaleListModel(
                id, title, description, imageSale, idRestaurant, titleRestaurant
            )
        }
    }
}