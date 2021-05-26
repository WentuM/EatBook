package com.example.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Date

@Entity(tableName = "review")
data class ReviewEntity(

    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String = "",
    @ColumnInfo(name = "text")
    val text: String = "",
    @ColumnInfo(name = "id_user")
    val idUser: String = "",
    @ColumnInfo(name = "date")
    val dateSend: String = "",
    @ColumnInfo(name = "rating")
    var rating: Double = 0.0,
    @ColumnInfo(name = "id_rest")
    val idRest: String = ""
)