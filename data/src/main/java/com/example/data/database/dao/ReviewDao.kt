package com.example.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.database.entity.FullReview
import com.example.data.database.entity.RestaurantEntity
import com.example.data.database.entity.ReviewEntity

@Dao
interface ReviewDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(reviewEntity: ReviewEntity)

    @Query("SELECT * FROM review WHERE id = :idReview")
    suspend fun getReviewById(idReview: String): ReviewEntity

    @Query("SELECT * FROM review")
    suspend fun getAllReviews(): List<ReviewEntity>

    @Query("SELECT review.id, review.text, review.date, review.rating, review.id_rest, user.username, user.image " +
            "FROM review, user " +
            "WHERE user.id == review.id_user")
    suspend fun getAllFullReview(): List<FullReview>
}