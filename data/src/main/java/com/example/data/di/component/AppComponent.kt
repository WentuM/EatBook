package com.example.data.di.component

import android.content.Context
import com.example.data.di.module.AppModule
import com.example.data.di.module.FireBaseModule
import com.example.data.repository.UserRepositoryImpl
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, FireBaseModule::class])
interface AppComponent {

    fun provideApp(): Context

    fun inject(userRepositoryImpl: UserRepositoryImpl)
}