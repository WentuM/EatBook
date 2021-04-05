package com.example.domain.interfaces

import com.example.domain.model.User
import com.google.firebase.auth.PhoneAuthCredential

interface UserRepository {

    fun getUserById(id: String): User

    suspend fun authUser(credential: PhoneAuthCredential): String

    suspend fun updateUser(name: String): String
}