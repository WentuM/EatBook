package com.example.eatbook.ui.sign.`in`.di

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.domain.UserInteractor
import com.example.eatbook.di.component.ViewModelKey
import com.example.eatbook.di.module.ViewModelModule
import com.example.eatbook.ui.sign.`in`.VerifyViewModel
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module(includes = [ViewModelModule::class])
class VerifyModule {

    @Provides
    @IntoMap
    @ViewModelKey(VerifyViewModel::class)
    fun provideViewModel(interactor: UserInteractor): ViewModel {
        return VerifyViewModel(interactor)
    }

    @Provides
    fun provideViewModelCreator(fragment: Fragment, viewModelFactory: ViewModelProvider.Factory): VerifyViewModel {
        return ViewModelProvider(fragment, viewModelFactory).get(VerifyViewModel::class.java)
    }
}