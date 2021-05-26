package com.example.data.repository

import android.content.Context
import com.example.data.database.dao.UserDao
import com.example.data.database.entity.UserEntity
import com.example.data.firebase.response.UserResponse
import com.example.data.mappers.UserConverter
import com.example.domain.interfaces.UserRepository
import com.example.domain.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await


class UserRepositoryImpl(
    private val userDao: UserDao,
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
    private val userConverterImpl: UserConverter
) : UserRepository {

    companion object {
        private const val USER_TABLE = "users"
        private const val USER_TABLE_COLUMN_ID = "id"
        private const val USER_TABLE_COLUMN_PHONE = "phone"
        private const val USER_TABLE_COLUMN_USERNAME = "username"
        private const val USER_TABLE_COLUMN_IMAGE = "image"
        private const val DEFAULT_USER_IMAGE =
            "https://firebasestorage.googleapis.com/v0/b/eatbook-5d561.appspot.com/o/users%2Fdefault-profile-picture1.jpg?alt=media&token=1e75488c-2fc5-46a9-9a18-39fd8f74847d"
    }

    override suspend fun getCurrentUser(): User {
        val userId = firebaseAuth.currentUser?.uid.toString()
        var user: User
        val userResponse: UserResponse?
        var userEntity: UserEntity = UserEntity()
        return try {
            userResponse =
                firestore.collection(USER_TABLE).document(userId).get().await()
                    .toObject(UserResponse::class.java)
            if (userResponse != null) {
                userEntity = userConverterImpl.fbtoDb(userResponse)
            }
            user = userConverterImpl.dbtoModel(userEntity)
            user
        } catch (e: Exception) {
            user = userConverterImpl.dbtoModel(userDao.getUserById(userId))
            user
        }
    }

    override suspend fun authUser(storedVerificationId: String, otp: String): String {
        var result = ""
        val credential: PhoneAuthCredential = PhoneAuthProvider.getCredential(
            storedVerificationId, otp
        )

        try {
            firebaseAuth.signInWithCredential(credential).await()
            result = if (existUser()) {
                "Успешный вход в аккаунт"
            } else {
                createUser()
            }
        } catch (e: Exception) {
            if (e is FirebaseAuthInvalidCredentialsException) {
                result = "Неправильный код"
            }
        }

        return result
    }

    override suspend fun updateUser(name: String): String {
        val uid = firebaseAuth.currentUser?.uid.toString()

        return try {
            val userMap = mutableMapOf<String, Any>()
            userMap[USER_TABLE_COLUMN_USERNAME] = name
            firestore.collection(USER_TABLE).document(uid).update(userMap).await()
            userDao.updateUser(name, uid)
            "Данные успешно изменены"
        } catch (e: Exception) {
            "Данные изменить не удалось"
        }
    }

    private suspend fun existUser(): Boolean {
        val uid = firebaseAuth.currentUser?.uid.toString()
        var result = true
        try {
            val user = firestore.collection("users").document(uid).get().await()
                .toObject(UserResponse::class.java)
            if (user == null) {
                result = false
            }
        } catch (e: Exception) {
            result = false
        }
        return result
    }

    private suspend fun createUser(): String {
        val uid = firebaseAuth.currentUser?.uid.toString()
        val phone = firebaseAuth.currentUser?.phoneNumber.toString()
        val userEntity = UserEntity(uid, phone, "", DEFAULT_USER_IMAGE)
        val userMap = mutableMapOf<String, Any>()
        userMap[USER_TABLE_COLUMN_ID] = uid
        userMap[USER_TABLE_COLUMN_PHONE] = phone
        userMap[USER_TABLE_COLUMN_USERNAME] = ""
        userMap[USER_TABLE_COLUMN_IMAGE] = DEFAULT_USER_IMAGE
        return try {
            firestore.collection(USER_TABLE).document(uid).set(userMap).await()
            userDao.insert(userEntity)
            "Успешный вход в аккаунт"
        } catch (e: Exception) {
            "Проверьте подключение к интернету"
        }
    }

    override suspend fun signOut(): String {
        val result = "Выход выполнен успешно"
        firebaseAuth.signOut()
        return result
    }

    override fun loggedUser(): Boolean {
        if (firebaseAuth.currentUser != null) {
            return true
        }
        return false
    }
}