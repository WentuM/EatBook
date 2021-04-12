package com.example.eatbook.ui.dashboard.di

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.domain.UserInteractor
import com.example.eatbook.ui.ViewModelFactory
import com.example.eatbook.ui.dashboard.ProfileViewModel
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module(includes = [ProfileViewModel::class])
class ProfileModule {

    @Provides
    @IntoMap
    fun provideViewModel(interactor: UserInteractor): ViewModel {
        return ProfileViewModel(interactor)
    }

    @Provides
    fun provideViewModelCreator(fragment: Fragment, viewModelFactory: ViewModelProvider.Factory): ProfileViewModel {
        return ViewModelProvider(fragment, viewModelFactory).get(ProfileViewModel::class.java)
    }
}