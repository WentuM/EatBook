package com.example.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.database.entity.ReviewEntity

@Dao
interface ReviewDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(reviewEntity: ReviewEntity)

    @Query("SELECT * FROM review WHERE id = :idReview")
    suspend fun getReviewById(idReview: String): ReviewEntity
}