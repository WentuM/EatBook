package com.example.domain

import android.app.Activity
import com.example.domain.interfaces.UserRepository
import com.example.domain.model.User
import com.google.firebase.auth.PhoneAuthCredential
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Named

class UserInteractor(
    private val userRepository: UserRepository,
    private val context: CoroutineContext
) {

    suspend fun getUserById(): User =
        withContext(context) {
            userRepository.getUserById()
        }

    suspend fun authUser(storedVerificationId: String, otp: String): String =
        withContext(context) {
            userRepository.authUser(storedVerificationId, otp)
        }


    suspend fun updateUser(nameUser: String, imageUser: String): String {
        return withContext(context) {
//            return runCatching {
//                return@runCatching withContext(context) {
            userRepository.updateUser(nameUser, imageUser)
////                }
//            }
        }
        //result<string> cannot return
        //withcontext внутри runcatching
        //if syncResult.

    }

    suspend fun signOutUser(): String =
        withContext(context) {
            userRepository.signOut()
        }

    fun getCurrentUser(): Boolean =
        userRepository.getCurrentUser()

//    fun getVerificationCode(numberPhone: String, activity: Activity): String =
//        userRepository.signIn(numberPhone, activity)
}