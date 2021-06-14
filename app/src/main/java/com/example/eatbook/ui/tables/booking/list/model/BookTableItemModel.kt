package com.example.eatbook.ui.tables.booking.list.model

data class BookTableItemModel(
    val id: String,
    val idTable: String,
    val day: String,
    val time: String,
    val countHour: Int,
    val imageTable: String,
    val nameTable: String,
    val idRestaurant: String,
    val nameRestaurant: String,
    val countPlaces: Int
)
