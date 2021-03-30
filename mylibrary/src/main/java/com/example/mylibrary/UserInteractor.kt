package com.example.domain

import com.example.domain.interfaces.UserRepository
import com.example.domain.model.User

class UserInteractor(
    private val userRepository: UserRepository
) {

    fun getUserByid(id: String): User {
        return userRepository.getUserById(id)
    }

    fun createUser(id: String, username: String, phoneNumber: String) {
        userRepository.createUser(id, username, phoneNumber)
    }
}