package com.example.eatbook.ui.sign.`in`

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.UserInteractor
import com.google.firebase.auth.PhoneAuthCredential
import kotlinx.coroutines.launch
import java.lang.Exception

class VerifyViewModel(
    private val userInteractor: UserInteractor
) : ViewModel() {

    private val mVerify: MutableLiveData<String> = MutableLiveData()

    fun verify(): LiveData<String> = mVerify

    fun onVerifyClick(credential: PhoneAuthCredential) {
        viewModelScope.launch {
            try {
               mVerify.value = userInteractor.authUser(credential)
            } catch (e: Exception) {

            }
        }
    }
}