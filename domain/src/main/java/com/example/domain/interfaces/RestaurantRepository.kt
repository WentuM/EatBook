package com.example.domain.interfaces

import com.example.domain.model.Restaurant

interface RestaurantRepository {

    suspend fun getListRestaurant(): List<Restaurant>

    suspend fun getRestaurantById(id: String): Restaurant
}