package com.example.data.mappers

import com.example.data.database.entity.RestaurantEntity
import com.example.data.database.entity.ReviewEntity
import com.example.domain.model.Restaurant
import com.example.domain.model.Review

class ReviewConverterImpl : ReviewConverter {
    override fun dbtoModel(reviewEntity: ReviewEntity): Review {
        return Review(
            reviewEntity.id,
            reviewEntity.text,
            reviewEntity.idUser,
            reviewEntity.dateSend,
            reviewEntity.raiting,
            reviewEntity.idRest
        )
    }

    override fun modeltoDb(review: Review): ReviewEntity {
        return ReviewEntity(
            review.id,
            review.text,
            review.userName,
            review.dateSend,
            review.rating,
            review.idRest
        )
    }
}