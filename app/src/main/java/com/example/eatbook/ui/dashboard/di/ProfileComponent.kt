package com.example.eatbook.ui.dashboard.di

import androidx.fragment.app.Fragment
import com.example.eatbook.ui.dashboard.ProfileFragment
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