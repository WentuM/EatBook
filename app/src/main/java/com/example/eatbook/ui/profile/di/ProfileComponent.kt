package com.example.eatbook.ui.profile.di

import androidx.fragment.app.Fragment
import com.example.eatbook.ui.profile.ProfileFragment
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent(modules = [ProfileModule::class])
interface ProfileComponent {

    @Subcomponent.Factory
    interface Factory {

        fun create(@BindsInstance fragment: Fragment): ProfileComponent
    }

    fun inject(profileFragment: ProfileFragment)
}