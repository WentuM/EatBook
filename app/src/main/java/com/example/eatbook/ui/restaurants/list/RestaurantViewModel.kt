package com.example.eatbook.ui.restaurants.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.interactor.RestaurantInteractor
import com.example.domain.model.Restaurant
import kotlinx.coroutines.launch
import java.lang.Exception

class RestaurantViewModel(
    private val restaurantInteractor: RestaurantInteractor
) : ViewModel() {

    private val _restaurants = MutableLiveData<List<Restaurant>>()
    private val _likeRest: MutableLiveData<String> = MutableLiveData()


    fun restaurants(): LiveData<List<Restaurant>> = _restaurants
    fun likeRest(): LiveData<String> = _likeRest

    fun getAllRestaurants() {
        viewModelScope.launch {
            try {
                _restaurants.value = restaurantInteractor.getAllRestaurant()
            } catch (e: Exception) {

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

}