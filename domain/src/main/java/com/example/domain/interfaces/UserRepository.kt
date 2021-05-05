package com.example.domain.interfaces

import android.app.Activity
import com.example.domain.model.User
import com.google.firebase.auth.PhoneAuthCredential

interface UserRepository {

    suspend fun getUserById(): User

    suspend fun authUser(storedVerificationId: String, otp: String): String

    suspend fun updateUser(name: String, image: String): String

    suspend fun signOut(): String

    fun getCurrentUser(): Boolean

//    fun signIn(numberPhone: String, activity: Activity): String
}