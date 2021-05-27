package com.example.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "favourite_rest")
data class FavouriteRestEntity(
    @PrimaryKey
    @ColumnInfo(name = "id_rest")
    val idRest: String
)