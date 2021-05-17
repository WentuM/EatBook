package com.example.domain.model

data class Review(
    val id: String,
    val text: String,
    val dateSend: String,
    val rating: Double,
    val idRest: String,
    var userImage: String = "",
    var userName: String = ""
)