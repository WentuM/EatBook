package com.example.data.di.module

import android.content.Context
import androidx.room.Room
import com.example.data.database.EatBookRoomDatabase
import com.example.data.database.dao.RestaurantDao
import com.example.data.database.dao.UserDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DbModule {

    @Provides
    @Singleton
    fun provideEatBookRoomDb(appModule: AppModule): EatBookRoomDatabase = Room.databaseBuilder(
        appModule.provideContext(),
        EatBookRoomDatabase::class.java,
        "eatbook_database"
    ).build()

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
}