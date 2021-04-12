package com.example.eatbook.di.module

import androidx.lifecycle.ViewModelProvider
import com.example.eatbook.di.component.ViewModelProviderFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelProviderFactory): ViewModelProvider.Factory
}