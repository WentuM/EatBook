package com.example.domain

import com.example.domain.interfaces.RestaurantRepository
import com.example.domain.model.Restaurant
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class RestaurantInteractor(
    private val restaurantRepository: RestaurantRepository,
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
}