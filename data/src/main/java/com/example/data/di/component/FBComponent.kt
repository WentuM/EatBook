package com.example.data.di.component

import com.example.data.di.module.FireBaseModule
import com.example.data.repository.UserRepositoryImpl
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [FireBaseModule::class])
interface FBComponent {

    fun inject(userRepositoryImpl: UserRepositoryImpl)
}