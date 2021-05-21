package com.example.domain.interfaces

import com.example.domain.model.Restaurant

interface FavouritesRepository {

    suspend fun getFavouriteRestaurant(): List<Restaurant>

    suspend fun setLikeForRestaurant(idRestaurant: String): String
}