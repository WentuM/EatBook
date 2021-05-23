package com.example.eatbook.ui.profile.list.booktable.di

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.domain.interactor.BookTableInteractor
import com.example.eatbook.di.component.ViewModelKey
import com.example.eatbook.di.module.ViewModelModule
import com.example.eatbook.ui.profile.list.booktable.MyBookTableViewModel
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module(includes = [ViewModelModule::class])
class MyBookTableModule {

    @Provides
    @IntoMap
    @ViewModelKey(MyBookTableViewModel::class)
    fun provideViewModel(interactor: BookTableInteractor): ViewModel {
        return MyBookTableViewModel(interactor)
    }

    @Provides
    fun provideViewModelCreator(fragment: Fragment, viewModelFactory: ViewModelProvider.Factory): MyBookTableViewModel {
        return ViewModelProvider(fragment, viewModelFactory).get(MyBookTableViewModel::class.java)
    }
}