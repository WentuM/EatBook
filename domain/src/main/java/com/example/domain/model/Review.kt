package com.example.domain.model

import java.sql.Date

data class Review(
    val id: String,
    val text: String,
    val idUser: String,
    val dateSend: Date
)