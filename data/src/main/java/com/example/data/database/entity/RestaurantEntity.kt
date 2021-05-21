package com.example.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "restaurant")
data class RestaurantEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: String = "",
    @ColumnInfo(name = "title")
    var title: String = "",
    @ColumnInfo(name = "description")
    var description: String = "",
    @ColumnInfo(name = "rating")
    var rating: Double = 0.0,
    @ColumnInfo(name = "image")
    var imageRest: String = "",
    @ColumnInfo(name = "price")
    var price: Int = 0,
    @ColumnInfo(name = "address")
    var address: String = "",
    @ColumnInfo(name = "listRest")
    var likeRest: Int = -1
)