package com.example.eatbook.ui.restaurants.list.model

data class RestaurantListModel(
    val id: String,
    val title: String,
    val description: String,
    var rating: Double,
    val imageRest: String,
    val price: Int,
    var address: String,
    var likeRest: Int
)
