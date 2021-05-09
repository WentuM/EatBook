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
    private val _getImage: MutableLiveData<String> = MutableLiveData()


    fun restaurants(): LiveData<List<Restaurant>> = _restaurants
    fun getImage(): LiveData<String>  = _getImage

    fun getAllRestaurants() {
        viewModelScope.launch {
            try {
                _restaurants.value = restaurantInteractor.getAllRestaurant()
            } catch (e: Exception) {

            }
        }
    }

    fun getImageB() {
        viewModelScope.launch {
            try {
                _getImage.value = restaurantInteractor.getImage()
            } catch (e: Exception) {

            }
        }
    }

}