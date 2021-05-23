package com.example.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table")
data class TableEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "count_places")
    val countPlaces: Int,
    @ColumnInfo(name = "image")
    val image: String,
    @ColumnInfo(name = "id_rest")
    val idRest: String,
    @ColumnInfo(name = "id_name")
    val nameRest: String
)