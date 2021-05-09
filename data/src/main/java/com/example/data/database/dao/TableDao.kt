package com.example.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.database.entity.TableEntity

@Dao
interface TableDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(tableEntity: TableEntity)

    @Query("SELECT * FROM `table` WHERE id = :idTable")
    suspend fun getTableById(idTable: String): TableEntity
}