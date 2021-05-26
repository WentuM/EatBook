package com.example.eatbook.ui.sign.`in`

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.interactor.UserInteractor
import kotlinx.android.synthetic.main.progress_bar.view.*
import kotlinx.coroutines.launch
import java.lang.Exception

class VerifyViewModel(
    private val userInteractor: UserInteractor
) : ViewModel() {

    private val mVerify: MutableLiveData<String> = MutableLiveData()

    fun verify(): LiveData<String> = mVerify

    fun onVerifyClick(storedVerificationId: String, otp: String, view: View) {
        viewModelScope.launch {
            try {
                view.progress_list.visibility = View.VISIBLE
               mVerify.value = userInteractor.authUser(storedVerificationId, otp)
            } catch (e: Exception) {
            } finally {
                view.progress_list.visibility = View.GONE
            }
        }
    }
}