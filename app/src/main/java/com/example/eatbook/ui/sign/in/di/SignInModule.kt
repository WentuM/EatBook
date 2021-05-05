package com.example.eatbook.ui.sign.`in`.di

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.domain.UserInteractor
import com.example.eatbook.di.component.ViewModelKey
import com.example.eatbook.di.module.ViewModelModule
import com.example.eatbook.ui.sign.`in`.SignInViewModel
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module(includes = [ViewModelModule::class])
class SignInModule {

    @Provides
    @IntoMap
    @ViewModelKey(SignInViewModel::class)
    fun provideViewModel(interactor: UserInteractor): ViewModel {
        return SignInViewModel(interactor)
    }

    @Provides
    fun provideViewModelCreator(fragment: Fragment, viewModelFactory: ViewModelProvider.Factory): SignInViewModel {
        return ViewModelProvider(fragment, viewModelFactory).get(SignInViewModel::class.java)
    }
}