package com.example.data.database.entity

import androidx.room.ColumnInfo

data class FullBookTable(
    val id: String,
    @ColumnInfo(name = "id_table")
    val idTable: String,
    val day: String,
    val time: String,
    @ColumnInfo(name = "count_hour")
    val countHour: Int,
    @ColumnInfo(name = "image")
    val imageTable: String,
    @ColumnInfo(name = "title")
    val nameTable: String,
    @ColumnInfo(name = "id_rest")
    val idRestaurant: String,
    @ColumnInfo(name = "name_rest")
    val nameRestaurant: String
)
