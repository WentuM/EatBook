package com.example.eatbook.ui.reviews.list.model

data class ReviewList(
    val id: String = "",
    val text: String = "",
    val userName: String = "",
    val dateSend: String = "",
    val rating: Double = 0.0,
    val idRest: String = "",
    var userImage: String = ""
)