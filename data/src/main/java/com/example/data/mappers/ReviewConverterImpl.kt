package com.example.data.mappers

import com.example.data.database.entity.RestaurantEntity
import com.example.data.database.entity.ReviewEntity
import com.example.data.database.entity.UserEntity
import com.example.data.firebase.response.ReviewResponse
import com.example.data.firebase.response.UserResponse
import com.example.domain.model.Restaurant
import com.example.domain.model.Review

class ReviewConverterImpl : ReviewConverter {

    override fun fbtoDb(reviewResponse: ReviewResponse): ReviewEntity {
        return ReviewEntity(
            reviewResponse.id,
            reviewResponse.text,
            reviewResponse.idUser,
            reviewResponse.dateSend,
            reviewResponse.rating,
            reviewResponse.idRest
        )
    }

    override fun modeltoFb(review: Review): ReviewResponse {
        TODO("Not yet implemented")
    }

    override fun dbtoModel(reviewEntity: ReviewEntity, userEntity: UserEntity): Review {
        return Review(
            reviewEntity.id,
            reviewEntity.text,
            reviewEntity.dateSend,
            reviewEntity.rating,
            reviewEntity.idRest,
            userEntity.image,
            userEntity.username
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