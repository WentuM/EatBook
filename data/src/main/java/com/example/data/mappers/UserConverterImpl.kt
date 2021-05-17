package com.example.data.mappers

import com.example.data.database.entity.UserEntity
import com.example.data.firebase.response.UserResponse
import com.example.domain.model.User

class UserConverterImpl: UserConverter {

    override fun dbtoFb(userEntity: UserEntity): UserResponse {
        return UserResponse(
            userEntity.id, userEntity.phone, userEntity.username, userEntity.image
        )
    }

    override fun fbtoDb(userResponse: UserResponse): UserEntity {
        return with(userResponse) {
            UserEntity(
                id, phone, username, image
            )
        }
    }

    override fun modeltoFb(user: User): UserResponse {
        TODO("Not yet implemented")
    }

    override fun dbtoModel(userEntity: UserEntity): User {
        TODO("Not yet implemented")
    }

    override fun modeltoDb(user: User): UserEntity {
        TODO("Not yet implemented")
    }
}