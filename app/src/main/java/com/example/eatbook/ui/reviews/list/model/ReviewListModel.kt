package com.example.eatbook.ui.reviews.list.model

data class ReviewListModel(
    val id: String,
    val text: String,
    val dateSend: String,
    val rating: Double,
    val idRest: String,
    var nameUser: String = "",
    var imageUser: String = ""
)