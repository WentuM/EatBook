package com.example.domain.model

import java.sql.Date

data class Review(
    val id: String,
    val text: String,
    val userName: String,
    val dateSend: String,
    val rating: Double,
    val idRest: String
)