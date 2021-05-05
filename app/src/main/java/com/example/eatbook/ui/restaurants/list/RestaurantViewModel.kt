package com.example.eatbook.ui.restaurants.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.RestaurantInteractor
import com.example.domain.model.Restaurant
import kotlinx.coroutines.launch
import java.lang.Exception

class RestaurantViewModel(
    private val restaurantInteractor: RestaurantInteractor
) : ViewModel() {

    private val _restaurants = MutableLiveData<List<Restaurant>>()
    fun restaurants(): LiveData<List<Restaurant>> = _restaurants

    fun getAllRestaurants() {
        viewModelScope.launch {
            try {
                _restaurants.value = restaurantInteractor.getAllRestaurant()
            } catch (e: Exception) {

            }
        }
    }

}