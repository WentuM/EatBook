package com.example.data.mappers

import com.example.data.database.entity.ReviewEntity
import com.example.data.database.entity.UserEntity
import com.example.data.firebase.response.ReviewResponse
import com.example.data.firebase.response.UserResponse
import com.example.domain.model.Review
import com.example.domain.model.User

interface UserConverter {

    fun dbtoFb(userEntity: UserEntity): UserResponse

    fun fbtoDb(userResponse: UserResponse): UserEntity

    fun modeltoFb(user: User): UserResponse

    fun dbtoModel(userEntity: UserEntity): User

    fun modeltoDb(user: User): UserEntity
}