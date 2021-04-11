package com.example.data.mappers

import com.example.data.database.entity.RestaurantEntity
import com.example.domain.model.Restaurant

interface RestaurantConverter {

    fun dbtoModel(restaurantEntity: RestaurantEntity): Restaurant

    fun modeltoDb(restaurant: Restaurant): RestaurantEntity
}