package com.example.data.repository

import android.content.Context
import android.util.Log
import com.example.data.database.dao.UserDao
import com.example.data.firebase.utilits.*
import com.example.domain.interfaces.UserRepository
import com.example.domain.model.User
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential


class UserRepositoryImpl(
    private val userDao: UserDao,
    private val context: Context
) : UserRepository {

    override fun getUserById(id: String): User {
        return User("", "", "")
    }

    override fun authUser(credential: PhoneAuthCredential): String {
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

    override fun updateUser(name: String): String {
        var result = ""
        val uid = AUTH.currentUser?.uid.toString()
        REF_DATABASE_ROOT.child(NODE_USERS).child(uid).child(CHILD_USERNAME).setValue(name)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    result = "Имя успешно изменено"
                }
            }
        return result
    }

    private fun createUser(): String {
        var result = ""
        val uid = AUTH.currentUser?.uid.toString()
        val dateMap = mutableMapOf<String, Any>()
        dateMap[CHILD_ID] = uid
        dateMap[CHILD_PHONE] = AUTH.currentUser?.phoneNumber.toString()
        Log.d("qwe2", AUTH.currentUser?.phoneNumber.toString())
        dateMap[CHILD_USERNAME] = "Danil"

        REF_DATABASE_ROOT.child(NODE_USERS).child(uid).updateChildren(dateMap)
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
}