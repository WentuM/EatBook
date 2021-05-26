package com.example.eatbook.ui.restaurants.detail.model

data class RestaurantItemModel(
    val id: String,
    val title: String,
    val description: String,
    var rating: Double,
    val imageRest: String,
    val price: Int,
    var address: String,
    var likeRest: Int
)
