package com.example.eatbook.ui.dashboard

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.UserInteractor
import com.example.domain.model.User
import kotlinx.coroutines.launch
import java.lang.Exception

class ProfileViewModel(
    private val userInteractor: UserInteractor
) : ViewModel() {

    private val mUpdateUser: MutableLiveData<String> = MutableLiveData()
    private val mGetUser: MutableLiveData<User> = MutableLiveData()
    private val signOut: MutableLiveData<String> = MutableLiveData()

    fun updateUser(): LiveData<String> = mUpdateUser
    fun getUser(): LiveData<User> = mGetUser
    fun signOutUser(): LiveData<String> = signOut

    fun onUpdateUserClick(newUsername: String, newUserImage: String) {
        viewModelScope.launch {
            try {
                mUpdateUser.value = userInteractor.updateUser(newUsername, newUserImage)
            } catch (e: Exception) {

            }
        }
    }

    fun onGetUser() {
        viewModelScope.launch {
            try {
                mGetUser.value = userInteractor.getUserById()
            } catch (e: Exception) {
                Log.d("qwe166", e.toString())
            }
        }
    }

    fun onSignOut() {
        viewModelScope.launch {
            signOut.value = userInteractor.signOutUser()
        }
    }
}