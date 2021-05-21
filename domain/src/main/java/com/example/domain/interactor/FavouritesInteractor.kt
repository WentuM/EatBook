package com.example.domain.interactor

import com.example.domain.interfaces.FavouritesRepository
import com.example.domain.model.Restaurant
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class FavouritesInteractor(
    private val favouritesRepository: FavouritesRepository,
    private val context: CoroutineContext
) {

    suspend fun getAllFavouritesRestaurant(): ArrayList<Restaurant> =
        withContext(context) {
            favouritesRepository.getFavouriteRestaurant() as ArrayList<Restaurant>
        }

    suspend fun createLikeRestaurant(idRestaurant: String) =
        withContext(context) {
            favouritesRepository.setLikeForRestaurant(idRestaurant)
        }
}