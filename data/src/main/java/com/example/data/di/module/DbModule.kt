package com.example.data.di.module

import android.content.Context
import androidx.room.Room
import com.example.data.database.EatBookRoomDatabase
import com.example.data.database.dao.*
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DbModule {

    @Provides
    @Singleton
    fun provideDatabase(context: Context): EatBookRoomDatabase {
        return EatBookRoomDatabase.getDataBase(context)
    }

    @Provides
    @Singleton
    fun provideUserDao(db: EatBookRoomDatabase): UserDao = db.userDao()

    @Provides
    @Singleton
    fun provideRestaurantDao(db: EatBookRoomDatabase): RestaurantDao = db.restaurantDao()

    @Provides
    @Singleton
    fun provideSaleDao(db: EatBookRoomDatabase): SaleDao = db.saleDao()

    @Provides
    @Singleton
    fun provideTableDao(db: EatBookRoomDatabase): TableDao = db.tableDao()

    @Provides
    @Singleton
    fun provideReviewDao(db: EatBookRoomDatabase): ReviewDao = db.reviewDao()
}