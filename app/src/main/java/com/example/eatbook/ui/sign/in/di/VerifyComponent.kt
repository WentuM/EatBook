package com.example.eatbook.ui.sign.`in`.di

import androidx.fragment.app.Fragment
import com.example.eatbook.ui.sign.`in`.VerifyFragment
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent(modules = [VerifyModule::class])
interface VerifyComponent {

    @Subcomponent.Factory
    interface Factory {

        fun create(@BindsInstance fragment: Fragment): VerifyComponent
    }

    fun inject(verifyFragment: VerifyFragment)
}