package com.example.data.firebase.response

data class ReviewResponse(
    val id: String = "",
    val text: String = "",
    val idUser: String = "",
    val dateSend: String = "",
    var rating: Double = 0.0,
    val idRest: String = "",
    val nameUser: String = "",
    val imageUser: String = ""
)