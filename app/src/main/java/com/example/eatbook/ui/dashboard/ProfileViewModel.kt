package com.example.eatbook.ui.dashboard

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

    fun updateUser(): LiveData<String> = mUpdateUser
    fun getUser(): LiveData<User> = mGetUser

    fun onUpdateUserClick(newUsername: String, newUserImage: String) {
        viewModelScope.launch {
            try {
                mUpdateUser.value = userInteractor.updateUser(newUsername, newUserImage)
            } catch (e: Exception) {

            }
        }
    }

    fun onGetUser(idUser: String) {
        viewModelScope.launch {
            try {
                mGetUser.value = userInteractor.getUserByid(idUser)
            } catch (e: Exception) {

            }
        }
    }
}