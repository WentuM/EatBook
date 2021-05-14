package com.example.eatbook.ui.restaurants.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.interactor.RestaurantInteractor
import com.example.domain.interactor.UserInteractor
import com.example.domain.model.Restaurant
import kotlinx.coroutines.launch

class RestaurantDetailViewModel(
    private val restaurantInteractor: RestaurantInteractor,
    private val userInteractor: UserInteractor
): ViewModel() {

    private val _getRestaurant: MutableLiveData<Restaurant> = MutableLiveData()


    fun getRestaurant(): LiveData<Restaurant> = _getRestaurant


    fun getRestaurantById(idRestaurant: String) {
        viewModelScope.launch {
            try {
                _getRestaurant.value = restaurantInteractor.getRestaurantById(idRestaurant)
            } catch (e: Exception) {

            }
        }
    }

    fun getCurrentUser(): Boolean = userInteractor.loggedUser()
}