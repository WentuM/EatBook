package com.example.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "book_table")
data class BookTableEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,
    @ColumnInfo(name = "id_user")
    val idUser: String,
    @ColumnInfo(name = "id_table")
    val idTable: String,
    @ColumnInfo(name = "day")
    val day: String,
    @ColumnInfo(name = "time")
    val time: String,
    @ColumnInfo(name = "count_hour")
    val countHour: Int
)