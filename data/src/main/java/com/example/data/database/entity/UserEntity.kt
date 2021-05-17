package com.example.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: String = "",
    @ColumnInfo(name = "phone")
    var phone: String = "",
    @ColumnInfo(name = "username")
    var username: String = "",
    @ColumnInfo(name = "image")
    var image: String = ""
)