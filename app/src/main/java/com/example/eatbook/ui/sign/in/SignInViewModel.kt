package com.example.eatbook.ui.sign.`in`

import androidx.lifecycle.ViewModel
import com.example.domain.interactor.UserInteractor

class SignInViewModel(
    private val userInteractor: UserInteractor
): ViewModel() {


    fun getCurrentUser(): Boolean = userInteractor.getCurrentUser()

//    fun getVerificationCode(numberPhone: String, activity: Activity): String = userInteractor.getVerificationCode(numberPhone, activity)
}