package com.example.data.repository

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.data.database.dao.UserDao
import com.example.data.database.entity.UserEntity
import com.example.domain.interfaces.UserRepository
import com.example.domain.model.User
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit
import kotlin.coroutines.coroutineContext
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
        private const val DEFAULT_USER_IMAGE = "https://firebasestorage.googleapis.com/v0/b/eatbook-5d561.appspot.com/o/users%2Fdefault-profile-picture1.jpg?alt=media&token=1e75488c-2fc5-46a9-9a18-39fd8f74847d"
    }

    override suspend fun getCurrentUser(): User {
        var userId = firebaseAuth.currentUser?.uid
        var user: User = User("", "", "", "")
//        firebaseAuth.uid?.let {
        return suspendCoroutine { continuation ->
            //suspendCancellableCoroutine
            userId?.let { it1 ->
                Log.d("qwe3", it1)
                firestore.collection(USER_TABLE).document(it1).get().addOnSuccessListener {
                    var userMap: HashMap<String, String> = it.data as HashMap<String, String>
                    var username = userMap[USER_TABLE_COLUMN_USERNAME].toString()
                    var userPhone = userMap[USER_TABLE_COLUMN_PHONE].toString()
                    var image = userMap[USER_TABLE_COLUMN_IMAGE].toString()
                    Log.d("qwe1", username)
                    Log.d("qwe2", userPhone)
                    user.id = userId
                    user.username = username
                    user.numberPhone = userPhone
                    user.image = image
                    continuation.resume(user)
                }.addOnFailureListener {
                    continuation.resumeWithException(it)
                    Log.d("qwe165", it.toString())
                }
            }
        }
    }

    override suspend fun authUser(storedVerificationId: String, otp: String): String {
        var result = ""
        val credential: PhoneAuthCredential = PhoneAuthProvider.getCredential(
            storedVerificationId, otp
        )

        var userExist = false
//        var userExist: Boolean = suspendCoroutine { continuation ->
            //suspendCancellableCoroutine
//            uid.let { it1 ->
//                firestore.collection("users").document(it1).get().addOnSuccessListener {
////                    var userMap: HashMap<String, String> = it.data as HashMap<String, String>
//                    if (it.data?.get(USER_TABLE_COLUMN_PHONE) == null) {
//                        continuation.resume(false)
//                    } else {
//                        continuation.resume(true)
//                    }
//                    if (it.exists()) {
//                        Log.d("qwe1", "true")
//                        continuation.resume(true)
//                    } else {
//                        Log.d("qwe1", "false")
//                        continuation.resume(false)
//                    }
//                }.addOnFailureListener {
//                    continuation.resumeWithException(it)
//                }
//            }
//        }
        return suspendCoroutine { continuation ->
            firebaseAuth.signInWithCredential(credential)
                .addOnSuccessListener {
                    Log.d("qwe13", firebaseAuth.currentUser?.uid.toString())
                    result = if (!userExist) {
                        //как здесь запустить метод в корутин скоупе, чтобы проверить существует ли пользователь
                        //метод suspend createUser()
                        createUser()
                    } else {
                        "Успешный вход в аккаунт"
                    }
                    Log.d("qwe12213", result)
                    continuation.resume(result)
                }.addOnFailureListener {
                    if (it is FirebaseAuthInvalidCredentialsException) {
                        result = "Неправильный код"
                    }
                    continuation.resume(result)
                }
        }
    }

    override suspend fun updateUser(userName: String, userImage: String): String {
        val uid = firebaseAuth.currentUser?.uid.toString()
//        REF_DATABASE_ROOT.child(NODE_USERS).child(uid).child(CHILD_USERNAME).setValue(name)
//            .addOnCompleteListener {
//                if (it.isSuccessful) {
//                    result = "Имя успешно изменено"
//                }
//            }
        val userMap = mutableMapOf<String, Any>()
        userMap[USER_TABLE_COLUMN_USERNAME] = userName
        userMap[USER_TABLE_COLUMN_IMAGE] = userImage
        return suspendCoroutine { continuation ->
            firestore.collection(USER_TABLE).add(userMap)
                .addOnSuccessListener {
                    var result = "Данные успешно изменены"
                    continuation.resume(result)
                }.addOnFailureListener {
                    Log.d("qwe0", it.message.toString())
                    continuation.resumeWithException(it)
                }
        }
    }

//    private suspend fun existUser(): Boolean {
//        val uid = firebaseAuth.currentUser?.uid.toString()
//        return suspendCoroutine { continuation ->
//            //suspendCancellableCoroutine
//            uid.let { it1 ->
//                firestore.collection("users").document(it1).get().addOnSuccessListener {
//                    if (it.exists()) {
//                        continuation.resume(true)
//                    } else {
//                        continuation.resume(false)
//                    }
//                }.addOnFailureListener {
//                    continuation.resumeWithException(it)
//                }
//            }
//        }
//    }

    private fun createUser(): String {
        var result = ""
        val uid = firebaseAuth.currentUser?.uid.toString()
        val userMap = mutableMapOf<String, Any>()
        userMap[USER_TABLE_COLUMN_ID] = uid
        userMap[USER_TABLE_COLUMN_PHONE] = firebaseAuth.currentUser?.phoneNumber.toString()
        userMap[USER_TABLE_COLUMN_USERNAME] = ""
        userMap[USER_TABLE_COLUMN_IMAGE] = DEFAULT_USER_IMAGE
//        return suspendCoroutine { continuation ->
        firestore.collection(USER_TABLE).document(uid).set(userMap)
            .addOnSuccessListener {
                result = "Успешный вход в аккаунт"
//                    continuation.resume(result)
            }.addOnFailureListener {
                result = it.toString()
//                    continuation.resumeWithException(it)
            }
//        }
        return result
    }

    override suspend fun signOut(): String {
        var result = "Выход выполнен успешно"
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