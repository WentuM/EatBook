package com.example.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "favourite_rest")
//    primaryKeys = ["id_user", "id_rest"],
//    foreignKeys = [
//        ForeignKey(
//            entity = UserEntity::class,
//            parentColumns = ["id"],
//            childColumns = ["id_user"]
//        ),
//        ForeignKey(
//            entity = RestaurantEntity::class,
//            parentColumns = ["id"],
//            childColumns = ["id_rest"]
//        )
//    ])
data class FavouriteRestEntity(
    @PrimaryKey
    @ColumnInfo(name = "id_rest")
    val idRest: String
)