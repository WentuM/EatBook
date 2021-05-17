package com.example.data.di.module

import android.content.Context
import com.example.data.database.dao.*
import com.example.data.repository.*
import com.example.domain.interfaces.*
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

    @Provides
    @Singleton
    fun provideSaleRepository(saleDao: SaleDao, context: Context, firebaseAuth: FirebaseAuth, firestore: FirebaseFirestore): SaleRepository =
        SaleRepositoryImpl(saleDao, context, firebaseAuth, firestore)

    @Provides
    @Singleton
    fun provideTableRepository(tableDao: TableDao, context: Context, firebaseAuth: FirebaseAuth, firestore: FirebaseFirestore): TableRepository =
        TableRepositoryImpl(tableDao, context, firebaseAuth, firestore)

    @Provides
    @Singleton
    fun provideReviewRepository(userDao: UserDao, reviewDao: ReviewDao, context: Context, firebaseAuth: FirebaseAuth, firestore: FirebaseFirestore): ReviewRepository =
        ReviewRepositoryImpl(userDao, reviewDao, context, firebaseAuth, firestore)
}