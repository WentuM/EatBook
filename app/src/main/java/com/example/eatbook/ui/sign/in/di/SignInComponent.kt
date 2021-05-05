package com.example.eatbook.ui.sign.`in`.di

import androidx.fragment.app.Fragment
import com.example.eatbook.ui.sign.`in`.SignInFragment
import com.example.eatbook.ui.sign.`in`.VerifyFragment
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent(modules = [SignInModule::class])
interface SignInComponent {

    @Subcomponent.Factory
    interface Factory {

        fun create(@BindsInstance fragment: Fragment): SignInComponent
    }

    fun inject(signInFragment: SignInFragment)
}