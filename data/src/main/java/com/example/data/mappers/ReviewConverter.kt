package com.example.data.mappers

import com.example.data.database.entity.ReviewEntity
import com.example.domain.model.Review

interface ReviewConverter {

    fun dbtoModel(reviewEntity: ReviewEntity): Review

    fun modeltoDb(review: Review): ReviewEntity
}