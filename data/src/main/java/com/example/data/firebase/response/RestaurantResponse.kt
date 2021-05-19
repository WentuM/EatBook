package com.example.data.firebase.response

data class Restaurant(
    val id: String = "",
    val title: String = "",
    val description: String = "",
    var rating: Double = 0.0,
    val imageRest: String = "",
    val price: Int = 0,
    var address: String = ""
)