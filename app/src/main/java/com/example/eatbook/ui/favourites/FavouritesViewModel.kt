package com.example.eatbook.ui.favourites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.interactor.FavouritesInteractor
import com.example.domain.model.Restaurant
import kotlinx.coroutines.launch
import java.lang.Exception

class FavouritesViewModel(
    private val favouritesInteractor: FavouritesInteractor
) : ViewModel() {

    private val _restaurants = MutableLiveData<List<Restaurant>>()
    private val _likeRest: MutableLiveData<String> = MutableLiveData()

    fun restaurants(): LiveData<List<Restaurant>> = _restaurants
    fun likeRest(): LiveData<String> = _likeRest

    fun getAllRestaurants() {
        viewModelScope.launch {
            try {
                _restaurants.value = favouritesInteractor.getAllFavouritesRestaurant()
            } catch (e: Exception) {

            }
        }
    }

    fun setLikeForRestaurant(idRest: String) {
        viewModelScope.launch {
            try {
                _likeRest.value = favouritesInteractor.createLikeRestaurant(idRest)
            } catch (e: Exception) {

            }
        }
    }
}