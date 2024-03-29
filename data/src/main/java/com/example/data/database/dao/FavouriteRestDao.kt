package com.example.data.database.dao

import androidx.room.*
import com.example.data.database.entity.FavouriteRestEntity
import com.example.data.database.entity.RestaurantEntity

@Dao
interface FavouriteRestDao {

    @Query("DELETE FROM favourite_rest")
    suspend fun deleteAll()

    @Delete
    suspend fun delete(favouriteRestEntity: FavouriteRestEntity)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(favouriteRestEntity: FavouriteRestEntity)

    @Query("SELECT * FROM restaurant LEFT JOIN favourite_rest ON restaurant.id = favourite_rest.id_rest WHERE favourite_rest.id_rest IS NOT NULL ")
    suspend fun getListFavourite(): List<RestaurantEntity>

    @Query("SELECT * FROM favourite_rest WHERE id_rest = :idRest")
    suspend fun getFavouriteEntity(idRest: String): FavouriteRestEntity?

    @Query("SELECT * FROM favourite_rest")
    suspend fun getAllFavouriteId(): List<FavouriteRestEntity>
}