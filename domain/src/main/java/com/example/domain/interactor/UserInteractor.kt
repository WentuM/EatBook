package com.example.domain.interactor

import com.example.domain.interfaces.UserRepository
import com.example.domain.model.User
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class UserInteractor(
    private val userRepository: UserRepository,
    private val context: CoroutineContext
) {

    suspend fun getCurrentUser(): User =
        withContext(context) {
            userRepository.getCurrentUser()
        }

    suspend fun authUser(storedVerificationId: String, otp: String): String =
        withContext(context) {
            userRepository.authUser(storedVerificationId, otp)
        }


    suspend fun updateUser(nameUser: String): String {
        return withContext(context) {
            userRepository.updateUser(nameUser)
        }
    }

    suspend fun signOutUser(): String =
        withContext(context) {
            userRepository.signOut()
        }

    fun loggedUser(): Boolean =
        userRepository.loggedUser()

//    fun getVerificationCode(numberPhone: String, activity: Activity): String =
//        userRepository.signIn(numberPhone, activity)
}