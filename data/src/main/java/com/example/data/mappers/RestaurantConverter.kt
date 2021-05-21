package com.example.data.mappers

import com.example.data.database.entity.RestaurantEntity
import com.example.data.database.entity.ReviewEntity
import com.example.data.firebase.response.RestaurantResponse
import com.example.data.firebase.response.ReviewResponse
import com.example.domain.model.Restaurant

interface RestaurantConverter {

    fun dbtoModel(restaurantEntity: RestaurantEntity): Restaurant

    fun modeltoDb(restaurant: Restaurant): RestaurantEntity

    fun fbtoDb(restaurantResponse: RestaurantResponse): RestaurantEntity
}