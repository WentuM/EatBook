package com.example.data.mappers

import com.example.data.database.entity.FullReview
import com.example.data.database.entity.ReviewEntity
import com.example.data.database.entity.UserEntity
import com.example.data.firebase.response.ReviewResponse
import com.example.data.firebase.response.UserResponse
import com.example.domain.model.Review

interface ReviewConverter {

    fun fbtoDb(reviewResponse: ReviewResponse): ReviewEntity

    fun fbtoModel(reviewResponse: ReviewResponse): Review

    fun dbtoModel(reviewEntity: ReviewEntity, userEntity: UserEntity): Review

    fun modeltoDb(review: Review): ReviewEntity

    fun reviewFullToModel(fullReview: FullReview): Review
}