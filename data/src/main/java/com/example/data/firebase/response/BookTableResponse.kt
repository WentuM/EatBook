package com.example.data.firebase.response

data class BookTableResponse(
    val id: String = "",
    val idUser: String = "",
    val idTable: String = "",
    val day: String = "",
    val time: String = "",
    val countHour: Int = 0,
    val imageTable: String = "",
    val nameTable: String = "",
    val idRestaurant: String = "",
    val nameRestaurant: String = "",
    val countPlaces: Int = 0
)
