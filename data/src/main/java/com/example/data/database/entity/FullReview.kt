package com.example.data.database.entity

import androidx.room.ColumnInfo

data class FullReview(
    val id: String = "",
    val text: String = "",
    @ColumnInfo(name = "date")
    val dateSend: String = "",
    var rating: Double = 0.0,
    @ColumnInfo(name = "id_rest")
    val idRest: String = "",
    @ColumnInfo(name = "username")
    val nameUser: String,
    @ColumnInfo(name = "image")
    val imageUser: String
)
