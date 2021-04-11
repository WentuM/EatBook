package com.example.data.repository

import android.content.Context
import com.example.data.database.dao.RestaurantDao
import com.example.domain.interfaces.RestaurantRepository
import com.example.domain.model.Restaurant

class RestaurantRepositoryImpl(
    private val restaurantDao: RestaurantDao,
    private val context: Context
) : RestaurantRepository {

    companion object {
        private const val RESTAURANTS_TABLE = "restaurants"
        private const val REST_TABLE_COLUMN_ID = "id"
        private const val REST_TABLE_COLUMN_TITLE = "title"
        private const val REST_TABLE_COLUMN_DESC = "description"
        private const val REST_TABLE_COLUMN_PRICE = "price"
        private const val REST_TABLE_COLUMN_RAITING = "raiting"
    }

    override suspend fun getListRestaurant(): ArrayList<Restaurant> {
        TODO("Not yet implemented")
    }

    override suspend fun getRestaurantById(id: String): Restaurant {
        TODO("Not yet implemented")
    }
}