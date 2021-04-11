package com.example.domain

import com.example.domain.interfaces.UserRepository
import com.example.domain.model.User
import com.google.firebase.auth.PhoneAuthCredential
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.withContext

class UserInteractor(
    private val userRepository: UserRepository,
    private val context: CoroutineContext
) {

    suspend fun getUserByid(id: String): User =
        withContext(context) {
            userRepository.getUserById(id)
        }

    suspend fun authUser(credential: PhoneAuthCredential): String =
        withContext(context) {
            userRepository.authUser(credential)
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
}