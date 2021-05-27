package com.example.data.di.module

import android.content.Context
import com.example.data.database.dao.*
import com.example.data.mappers.*
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
    fun provideUserRepository(
        userDao: UserDao,
        firebaseAuth: FirebaseAuth,
        favouriteRestDao: FavouriteRestDao,
        firestore: FirebaseFirestore,
        userConverter: UserConverter
    ): UserRepository =
        UserRepositoryImpl(userDao, favouriteRestDao, firebaseAuth, firestore, userConverter)

    @Provides
    fun provideRestaurantRepository(
        restaurantDao: RestaurantDao,
        favouriteRestDao: FavouriteRestDao,
        firestore: FirebaseFirestore,
        restaurantConverter: RestaurantConverter
    ): RestaurantRepository =
        RestaurantRepositoryImpl(
            restaurantDao,
            favouriteRestDao,
            firestore,
            restaurantConverter
        )

    @Provides
    fun provideSaleRepository(
        saleDao: SaleDao,
        firestore: FirebaseFirestore,
        saleConverter: SaleConverter
    ): SaleRepository =
        SaleRepositoryImpl(saleDao, firestore, saleConverter)

    @Provides
    fun provideTableRepository(
        tableDao: TableDao,
        firestore: FirebaseFirestore,
        tableConverter: TableConverter
    ): TableRepository =
        TableRepositoryImpl(tableDao, firestore, tableConverter)

    @Provides
    fun provideReviewRepository(
        reviewDao: ReviewDao,
        firebaseAuth: FirebaseAuth,
        firestore: FirebaseFirestore,
        reviewConverter: ReviewConverter
    ): ReviewRepository =
        ReviewRepositoryImpl(reviewDao, firebaseAuth, firestore, reviewConverter)

    @Provides
    fun provideBookTableRepository(
        bookTableDao: BookTableDao,
        firebaseAuth: FirebaseAuth,
        firestore: FirebaseFirestore,
        bookTableConverter: BookTableConverter
    ): BookTableRepository =
        BookTableRepositoryImpl(bookTableDao, firebaseAuth, firestore, bookTableConverter)

    @Provides
    fun provideFavouriteRestRepository(
        favouriteRestDao: FavouriteRestDao,
        firebaseAuth: FirebaseAuth,
        firestore: FirebaseFirestore,
        restaurantConverter: RestaurantConverter
    ): FavouritesRepository =
        FavouritesRepositoryImpl(
            favouriteRestDao,
            firebaseAuth,
            firestore,
            restaurantConverter
        )
}