package com.example.data.repository

import android.content.Context
import android.util.Log
import com.example.data.database.dao.UserDao
import com.example.data.database.entity.UserEntity
import com.example.data.firebase.response.UserResponse
import com.example.data.mappers.UserConverterImpl
import com.example.domain.interfaces.UserRepository
import com.example.domain.model.User
import com.google.firebase.auth.*
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine


class UserRepositoryImpl(
    private val userDao: UserDao,
    private val context: Context,
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore
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
        val userConverterImpl = UserConverterImpl()
        val userId = firebaseAuth.currentUser?.uid.toString()
        var user: User = User()
        var userResponse: UserResponse? = UserResponse()
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
            //db
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
            "Данные успешно изменены"
        } catch (e: Exception) {
            "Данные изменить не удалось"
        }
    }

    private suspend fun existUser(): Boolean {
        val uid = firebaseAuth.currentUser?.uid.toString()
        var result = true
        //suspendCancellableCoroutine
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
        val userMap = mutableMapOf<String, Any>()
        userMap[USER_TABLE_COLUMN_ID] = uid
        userMap[USER_TABLE_COLUMN_PHONE] = firebaseAuth.currentUser?.phoneNumber.toString()
        userMap[USER_TABLE_COLUMN_USERNAME] = ""
        userMap[USER_TABLE_COLUMN_IMAGE] = DEFAULT_USER_IMAGE
        return try {
            firestore.collection(USER_TABLE).document(uid).set(userMap).await()
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

//    override fun signIn(numberPhone: String, activity: Activity): String {
//        var result = ""
//        var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
//        // Callback function for Phone Auth
////        suspendCoroutine { continuation ->
//            callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//
//                override fun onVerificationCompleted(credential: PhoneAuthCredential) {
//                }
//
//                override fun onVerificationFailed(e: FirebaseException) {
//                    result = e.toString()
//                }
//
//                override fun onCodeSent(
//                    verificationId: String,
//                    token: PhoneAuthProvider.ForceResendingToken
//                ) {
//                    result = verificationId
//                    Log.d("qwe123", verificationId)
//                }
//            }
//        }
//        val options = PhoneAuthOptions.newBuilder(firebaseAuth)
//            .setPhoneNumber(numberPhone) // Phone number to verify
//            .setActivity(activity)
//            .setTimeout(60, TimeUnit.SECONDS) // Timeout and unit
//            .setCallbacks(callbacks) // OnVerificationStateChangedCallbacks
//            .build()
//        PhoneAuthProvider.verifyPhoneNumber(options)
//        Log.d("qwe124", result)
//        return result
//    }

//    private fun get(): String {
//        return object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//
//            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
//            }
//
//            override fun onVerificationFailed(e: FirebaseException) {
//                e.toString()
//            }
//
//            override fun onCodeSent(
//                verificationId: String,
//                token: PhoneAuthProvider.ForceResendingToken
//            ) {
//                verificationId
//                Log.d("qwe123", verificationId)
//            }
//        }
//    }

//    private fun generateCallbacks(): PhoneAuthProvider.OnVerificationStateChangedCallbacks? {
//        return object : OnVerificationStateChangedCallbacks() {
//            fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {
//                this@MultiFactorSignInActivity.mPhoneAuthCredential = phoneAuthCredential
//                mBinding.finishMfaSignIn.performClick()
//                Toast.makeText(
//                    this@MultiFactorSignInActivity, "Verification complete!", Toast.LENGTH_SHORT
//                )
//                    .show()
//            }
//
//            fun onCodeSent(
//                verificationId: String,
//                token: PhoneAuthProvider.ForceResendingToken
//            ) {
//                this@MultiFactorSignInActivity.mVerificationId = verificationId
//                mBinding.finishMfaSignIn.setClickable(true)
//            }
//
//            fun onVerificationFailed(e: FirebaseException) {
//                Toast.makeText(
//                    this@MultiFactorSignInActivity, "Error: " + e.message, Toast.LENGTH_SHORT
//                )
//                    .show()
//            }
//        }
//    }
}