package com.example.eatbook.ui.restaurants.list

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.interactor.RestaurantInteractor
import com.example.domain.model.Restaurant
import com.example.eatbook.ui.restaurants.detail.model.RestaurantItemModel
import com.example.eatbook.ui.restaurants.list.model.RestaurantListModel
import kotlinx.android.synthetic.main.progress_bar.view.*
import kotlinx.coroutines.launch
import java.lang.Exception

class RestaurantViewModel(
    private val restaurantInteractor: RestaurantInteractor
) : ViewModel() {

    private val _restaurants = MutableLiveData<List<RestaurantListModel>>()
    private val _likeRest: MutableLiveData<String> = MutableLiveData()


    fun restaurants(): LiveData<List<RestaurantListModel>> = _restaurants
    fun likeRest(): LiveData<String> = _likeRest

    fun getAllRestaurants(view: View) {
        viewModelScope.launch {
            try {
                view.progress_list.visibility = View.VISIBLE
                _restaurants.value = restaurantInteractor.getAllRestaurant()
                    .map { mapRestaurantToRestaurantListModel(it) }
            } catch (e: Exception) {

            } finally {
                view.progress_list.visibility = View.GONE
            }
        }
    }

    fun setLikeForRestaurant(idRest: String) {
        viewModelScope.launch {
            try {
                _likeRest.value = restaurantInteractor.setLikeForRestaurant(idRest)
            } catch (e: Exception) {

            }
        }
    }

    private fun mapRestaurantToRestaurantListModel(restaurant: Restaurant): RestaurantListModel {
        return with(restaurant) {
            RestaurantListModel(
                id, title, description, rating, imageRest, price, address, likeRest
            )
        }
    }

}