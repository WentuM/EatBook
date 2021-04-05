package com.example.data.repository

import android.content.Context
import com.example.data.database.dao.RestaurantDao
import com.example.domain.interfaces.RestaurantRepository
import com.example.domain.model.Restaurant

class RestaurantRepositoryImpl(
    private val restaurantDao: RestaurantDao,
    private val context: Context
): RestaurantRepository {
    override suspend fun getListRestaurant(): ArrayList<Restaurant> {
        TODO("Not yet implemented")
    }

    override suspend fun getRestaurantById(id: String): Restaurant {
        TODO("Not yet implemented")
    }
}