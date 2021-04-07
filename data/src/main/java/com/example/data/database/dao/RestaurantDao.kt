package com.example.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.database.entity.RestaurantEntity


@Dao
interface RestaurantDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(restaurantEntity: RestaurantEntity)

    @Query("SELECT * FROM restaurant WHERE id = :idRestaurant")
    suspend fun getUserById(idRestaurant: String): RestaurantEntity
}