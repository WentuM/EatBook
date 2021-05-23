package com.example.eatbook.ui.tables.booking.list.model

data class TableItemModel(
    val id: String,
    val title: String,
    val countPlaces: Int,
    val image: String,
    val idRestaurant: String,
    val nameRestaurant: String
)