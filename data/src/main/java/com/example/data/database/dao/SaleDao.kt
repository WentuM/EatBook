package com.example.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.database.entity.RestaurantEntity
import com.example.data.database.entity.SaleEntity

@Dao
interface SaleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertList(listSaleEntity: List<SaleEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(saleEntity: SaleEntity)

    @Query("SELECT * FROM sale WHERE id = :idSale")
    suspend fun getSaleById(idSale: String): SaleEntity

    @Query("SELECT * FROM sale")
    suspend fun getAllSales(): List<SaleEntity>
}