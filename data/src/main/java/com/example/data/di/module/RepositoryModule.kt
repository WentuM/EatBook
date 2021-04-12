package com.example.data.di.module

import android.content.Context
import com.example.data.database.dao.UserDao
import com.example.data.repository.UserRepositoryImpl
import com.example.domain.interfaces.UserRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideUserRepository(userDao: UserDao, context: Context): UserRepository =
        UserRepositoryImpl(userDao, context)
}