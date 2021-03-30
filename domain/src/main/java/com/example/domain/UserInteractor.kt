package com.example.domain

import com.example.domain.interfaces.UserRepository
import com.example.domain.model.User
import com.google.firebase.auth.PhoneAuthCredential

class UserInteractor(
    private val userRepository: UserRepository
) {

    fun getUserByid(id: String): User {
        return userRepository.getUserById(id)
    }

    fun authUser(credential: PhoneAuthCredential): String {
        return userRepository.authUser(credential)
    }

    fun updateUser(nameUser: String): String {
        return userRepository.updateUser(nameUser)
    }
}