package com.example.domain.interfaces

import com.example.domain.model.Review

interface ReviewRepository {

    suspend fun getListReview(): List<Review>

    suspend fun getReviewById(id: String): Review

}