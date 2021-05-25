package com.example.data.mappers

import com.example.data.database.entity.ReviewEntity
import com.example.data.database.entity.UserEntity
import com.example.data.firebase.response.ReviewResponse
import com.example.domain.model.Review

class ReviewConverterImpl : ReviewConverter {

    override fun fbtoDb(reviewResponse: ReviewResponse): ReviewEntity =
        with(reviewResponse) {
            ReviewEntity(
                id,
                text,
                idUser,
                dateSend,
                rating,
                idRest
            )
        }

    override fun fbtoModel(reviewResponse: ReviewResponse): Review =
        with(reviewResponse) {
            Review(
                id, text, dateSend, rating, idRest, nameUser, imageUser
            )
        }

    override fun modeltoFb(review: Review): ReviewResponse {
        TODO("Not yet implemented")
    }

    override fun dbtoModel(reviewEntity: ReviewEntity, userEntity: UserEntity): Review =
        with(reviewEntity) {
            Review(
                id,
                text,
                dateSend,
                rating,
                idRest,
                userEntity.image,
                userEntity.username
            )
        }

    override fun modeltoDb(review: Review): ReviewEntity =
        ReviewEntity(
            review.id,
            review.text,
            review.nameUser,
            review.dateSend,
            review.rating,
            review.idRest
        )
}