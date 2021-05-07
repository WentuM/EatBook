package com.example.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.database.entity.SaleEntity

@Dao
interface SaleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(saleEntity: SaleEntity)

    @Query("SELECT * FROM restaurant WHERE id = :idSale")
    suspend fun getSaleById(idSale: String): SaleEntity
}