package com.example.eatbook.ui.restaurants.detail

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.interactor.RestaurantInteractor
import com.example.domain.interactor.UserInteractor
import com.example.domain.model.Restaurant
import com.example.eatbook.ui.favourites.model.FavouritesListModel
import com.example.eatbook.ui.restaurants.detail.model.RestaurantItemModel
import kotlinx.android.synthetic.main.fragment_restaurant_detail.view.*
import kotlinx.android.synthetic.main.progress_bar.view.*
import kotlinx.coroutines.launch

class RestaurantDetailViewModel(
    private val restaurantInteractor: RestaurantInteractor
) : ViewModel() {

    private val _getRestaurant: MutableLiveData<RestaurantItemModel> = MutableLiveData()


    fun getRestaurant(): LiveData<RestaurantItemModel> = _getRestaurant


    fun getRestaurantById(idRestaurant: String, view: View) {
        viewModelScope.launch {
            try {
                view.progress_list.visibility = View.VISIBLE
                _getRestaurant.value = mapRestaurantToRestaurantItemModel(
                    restaurantInteractor.getRestaurantById(idRestaurant)
                )
            } catch (e: Exception) {

            } finally {
                view.progress_list.visibility = View.GONE
            }
        }
    }

    fun getCurrentUser(): Boolean = restaurantInteractor.loggedUser()

    private fun mapRestaurantToRestaurantItemModel(restaurant: Restaurant): RestaurantItemModel {
        return with(restaurant) {
            RestaurantItemModel(
                id, title, description, rating, imageRest, price, address, likeRest
            )
        }
    }
}