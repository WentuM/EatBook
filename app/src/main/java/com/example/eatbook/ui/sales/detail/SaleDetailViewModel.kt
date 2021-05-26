package com.example.eatbook.ui.sales.detail

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.interactor.SaleInteractor
import com.example.domain.model.Restaurant
import com.example.domain.model.Review
import com.example.domain.model.Sale
import com.example.eatbook.ui.reviews.list.model.ReviewListModel
import com.example.eatbook.ui.sales.detail.model.SaleItemModel
import kotlinx.android.synthetic.main.progress_bar.view.*
import kotlinx.coroutines.launch

class SaleDetailViewModel(
    private val saleInteractor: SaleInteractor
) : ViewModel() {

    private val _getSale: MutableLiveData<SaleItemModel> = MutableLiveData()


    fun getSale(): LiveData<SaleItemModel> = _getSale


    fun getSaleById(idSale: String, view: View) {
        viewModelScope.launch {
            try {
                view.progress_list.visibility = View.VISIBLE
                _getSale.value = mapSaleToSaleItemModel(saleInteractor.getSaleById(idSale))
            } catch (e: Exception) {

            } finally {
                view.progress_list.visibility = View.GONE
            }
        }
    }

    private fun mapSaleToSaleItemModel(sale: Sale): SaleItemModel {
        return with(sale) {
            SaleItemModel(
                id, title, description, imageSale, idRestaurant, titleRestaurant
            )
        }
    }
}