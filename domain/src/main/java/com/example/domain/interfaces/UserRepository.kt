package com.example.domain.interfaces

import android.app.Activity
import com.example.domain.model.User
import com.google.firebase.auth.PhoneAuthCredential

interface UserRepository {

    suspend fun getCurrentUser(): User

    suspend fun authUser(storedVerificationId: String, otp: String): String

    suspend fun updateUser(name: String): String

    suspend fun signOut(): String

    fun loggedUser(): Boolean

//    fun signIn(numberPhone: String, activity: Activity): String
}