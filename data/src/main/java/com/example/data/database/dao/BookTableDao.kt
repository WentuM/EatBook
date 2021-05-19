package com.example.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.database.entity.BookTableEntity

@Dao
interface BookTableDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(bookTableEntity: BookTableEntity)

    @Query("SELECT * FROM book_table WHERE id = :idBookTable")
    suspend fun getBookTableById(idBookTable: String): BookTableEntity
}