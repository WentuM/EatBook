package com.example.domain.model

data class Restaurant(
    val id: String,
    val title: String,
    val description: String,
    var rating: Double,
    val imageRest: String,
    val price: Int,
    var address: String
)