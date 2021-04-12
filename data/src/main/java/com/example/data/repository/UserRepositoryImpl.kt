package com.example.data.repository

import android.content.Context
import android.util.Log
import com.example.data.database.dao.UserDao
import com.example.data.database.entity.UserEntity
import com.example.data.firebase.utilits.*
import com.example.domain.interfaces.UserRepository
import com.example.domain.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.database.DatabaseReference
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class UserRepositoryImpl(
    private val userDao: UserDao,
    private val context: Context
) : UserRepository {

    @Inject
    lateinit var firebaseAuth: FirebaseAuth

    @Inject
    lateinit var databaseReference: DatabaseReference

    companion object {
        private const val USER_TABLE = "users"
        private const val USER_TABLE_COLUMN_ID = "id"
        private const val USER_TABLE_COLUMN_PHONE = "phone"
        private const val USER_TABLE_COLUMN_USERNAME = "username"
        private const val USER_TABLE_COLUMN_IMAGE = "image"
    }

    override suspend fun getUserById(): User {
//        com.example.eatbook.EatBookApp.appComponent.inject(this)
        var userEntity: UserEntity
        var user: User = User("", "")
//        REF_DATABASE_ROOT.child(NODE_USERS).child(id)
//            .addListenerForSingleValueEvent(object : ValueEventListener {
//                override fun onCancelled(error: DatabaseError) {
//                    TODO("Not yet implemented")
//                }
//
//                override fun onDataChange(snapshot: DataSnapshot) {
//                    var userMap: HashMap<String, String> = snapshot.value as HashMap<String, String>
//                    var username = userMap[CHILD_USERNAME]
//                    var userPhone = userMap[CHILD_PHONE]
//                    if (username != null && userPhone != null) {
//                        user = User(username, userPhone)
//                    }
//                    Log.d("qwe10", snapshot.value.toString())
//                }
//
//            })
        firebaseAuth.uid?.let {
            return suspendCoroutine { continuation ->
                //suspendCancellableCoroutine
                REF_DATABASE_ROOT.child(USER_TABLE).child(it).get().addOnSuccessListener {
                    Log.d("qwe12", it.value.toString())
                    var userMap: HashMap<String, String> = it.value as HashMap<String, String>
                    var username = userMap[USER_TABLE_COLUMN_USERNAME]
                    var userPhone = userMap[USER_TABLE_COLUMN_PHONE]
                    Log.d("qwe1", "$username")
                    Log.d("qwe2", "$userPhone")
                    if (username != null && userPhone != null) {
                        user.username = username
                        user.numberPhone = userPhone
                    }
                    continuation.resume(user)
                }.addOnFailureListener {
                    continuation.resumeWithException(it)
                    Log.d("qwe165", it.toString())
                }
            }
        }
        Log.d("qwe11", user.toString())
        // 11 раньше 10 выполняется, callback как фиксить(( (решение продумать, flow, блокировка)
        return user
    }

    override suspend fun authUser(credential: PhoneAuthCredential): String {
//        com.example.eatbook.EatBookApp.appComponent.inject(this)
        var result = ""
        AUTH.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("qwe13", AUTH.currentUser?.uid.toString())
                    result = createUser()
                } else {
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                        result = "Неправильный код"
                    }
                }
            }
        return result
    }

    override suspend fun updateUser(userName: String, userImage: String): String {
        var result = ""
        val uid = AUTH.currentUser?.uid.toString()
//        REF_DATABASE_ROOT.child(NODE_USERS).child(uid).child(CHILD_USERNAME).setValue(name)
//            .addOnCompleteListener {
//                if (it.isSuccessful) {
//                    result = "Имя успешно изменено"
//                }
//            }
        val dateMap = mutableMapOf<String, Any>()
        dateMap[USER_TABLE_COLUMN_USERNAME] = userName
        dateMap[USER_TABLE_COLUMN_IMAGE] = userImage
        REF_DATABASE_ROOT.child(USER_TABLE).child(uid).updateChildren(dateMap)
            .addOnCompleteListener { task2 ->
                if (task2.isSuccessful) {
                    result = "Данные успешно изменены"
                } else {

                }
            }
        return result
    }

    private fun createUser(): String {
        var result = ""
        val uid = AUTH.currentUser?.uid.toString()
        val dateMap = mutableMapOf<String, Any>()
        dateMap[USER_TABLE_COLUMN_ID] = uid
        dateMap[USER_TABLE_COLUMN_PHONE] = AUTH.currentUser?.phoneNumber.toString()
        Log.d("qwe2", AUTH.currentUser?.phoneNumber.toString())
        dateMap[USER_TABLE_COLUMN_USERNAME] = ""

        REF_DATABASE_ROOT.child(USER_TABLE).child(uid).updateChildren(dateMap)
            .addOnCompleteListener { task2 ->
                if (task2.isSuccessful) {
                    result = "Успешный вход в аккаунт"
                } else {
                    result = task2.exception?.message.toString()
                    Log.d("qwe0", task2.exception?.message.toString())
                }
            }
        return result
    }

    override suspend fun signOut(): String {
//        com.example.eatbook.EatBookApp.appComponent.inject(this)
        var result = "Выход выполнен успешно"
        firebaseAuth.signOut()
        return result
    }
}