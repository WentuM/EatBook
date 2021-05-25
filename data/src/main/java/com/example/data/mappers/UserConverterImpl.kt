package com.example.data.mappers

import com.example.data.database.entity.UserEntity
import com.example.data.firebase.response.UserResponse
import com.example.domain.model.User

class UserConverterImpl : UserConverter {

    override fun dbtoFb(userEntity: UserEntity): UserResponse =
        with(userEntity) {
            UserResponse(
                id, phone, username, image
            )
        }

    override fun fbtoDb(userResponse: UserResponse): UserEntity =
        with(userResponse) {
            UserEntity(
                id, phone, username, image
            )
        }

    override fun modeltoFb(user: User): UserResponse {
        TODO("Not yet implemented")
    }

    override fun dbtoModel(userEntity: UserEntity): User =
        with(userEntity) {
            User(id, phone, username, image)
        }

    override fun modeltoDb(user: User): UserEntity {
        TODO("Not yet implemented")
    }
}