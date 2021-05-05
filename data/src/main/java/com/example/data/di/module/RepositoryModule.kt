package com.example.data.di.module

import android.content.Context
import com.example.data.database.dao.RestaurantDao
import com.example.data.database.dao.UserDao
import com.example.data.repository.RestaurantRepositoryImpl
import com.example.data.repository.UserRepositoryImpl
import com.example.domain.interfaces.RestaurantRepository
import com.example.domain.interfaces.UserRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideUserRepository(userDao: UserDao, context: Context, firebaseAuth: FirebaseAuth, firestore: FirebaseFirestore): UserRepository =
        UserRepositoryImpl(userDao, context, firebaseAuth, firestore)

    @Provides
    @Singleton
    fun provideRestaurantRepository(restaurantDao: RestaurantDao, context: Context, firebaseAuth: FirebaseAuth, firestore: FirebaseFirestore): RestaurantRepository =
        RestaurantRepositoryImpl(restaurantDao, context, firebaseAuth, firestore)
}