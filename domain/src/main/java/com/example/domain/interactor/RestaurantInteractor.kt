package com.example.domain.interactor

import com.example.domain.interfaces.FavouritesRepository
import com.example.domain.interfaces.RestaurantRepository
import com.example.domain.model.Restaurant
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class RestaurantInteractor(
    private val restaurantRepository: RestaurantRepository,
    private val favouritesRepository: FavouritesRepository,
    private val context: CoroutineContext
) {
    suspend fun getRestaurantById(id: String): Restaurant =
        withContext(context) {
            restaurantRepository.getRestaurantById(id)
        }

    suspend fun getAllRestaurant(): ArrayList<Restaurant> =
        withContext(context) {
            restaurantRepository.getListRestaurant() as ArrayList<Restaurant>
        }

    suspend fun getImage(): String =
        withContext(context) {
            restaurantRepository.getRestaurantImage()
        }

    suspend fun setLikeForRestaurant(idRest: String) =
        withContext(context) {
            favouritesRepository.setLikeForRestaurant(idRest)
        }
}