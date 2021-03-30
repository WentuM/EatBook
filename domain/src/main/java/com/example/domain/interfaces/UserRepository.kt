package com.example.domain.interfaces

import com.example.domain.model.User
import com.google.firebase.auth.PhoneAuthCredential

interface UserRepository {

    fun getUserById(id: String): User

    fun authUser(credential: PhoneAuthCredential): String

    fun updateUser(name: String): String
}