package com.example.domain.interfaces

import com.example.domain.model.Review

interface ReviewRepository {

    suspend fun getListReview(idRestaurant: String): List<Review>

    suspend fun getReviewById(id: String): Review

    suspend fun createReviewByUser(review: Review): String

}