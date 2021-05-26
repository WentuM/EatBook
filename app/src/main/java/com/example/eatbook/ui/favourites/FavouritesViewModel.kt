package com.example.eatbook.ui.favourites

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.interactor.FavouritesInteractor
import com.example.domain.model.Restaurant
import com.example.domain.model.Table
import com.example.eatbook.ui.favourites.model.FavouritesListModel
import com.example.eatbook.ui.tables.booking.list.model.TableItemModel
import kotlinx.android.synthetic.main.progress_bar.view.*
import kotlinx.coroutines.launch

class FavouritesViewModel(
    private val favouritesInteractor: FavouritesInteractor
) : ViewModel() {

    private val _restaurants = MutableLiveData<List<FavouritesListModel>>()
    private val _likeRest: MutableLiveData<String> = MutableLiveData()

    fun restaurants(): LiveData<List<FavouritesListModel>> = _restaurants
    fun likeRest(): LiveData<String> = _likeRest

    fun getAllRestaurants(view: View) {
        viewModelScope.launch {
            try {
                view.progress_list.visibility = View.VISIBLE
                _restaurants.value = favouritesInteractor.getAllFavouritesRestaurant()
                    .map { mapRestaurantToFavouritesListModel(it) }
            } catch (e: Exception) {

            } finally {
                view.progress_list.visibility = View.GONE
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

    //засетить в нужный элемент like = true, перерисовать в лайвдату новый список
    //deep copy list

    private fun mapRestaurantToFavouritesListModel(restaurant: Restaurant): FavouritesListModel {
        return with(restaurant) {
            FavouritesListModel(
                id, title, description, rating, imageRest, price, address, likeRest
            )
        }
    }
}