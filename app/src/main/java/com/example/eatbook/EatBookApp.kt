package com.example.eatbook

import android.app.Application
import com.example.data.database.EatBookRoomDatabase
import com.example.data.repository.RestaurantRepositoryImpl
import com.example.data.repository.UserRepositoryImpl
import com.example.domain.interfaces.RestaurantRepository

class EatBookApp : Application() {

    val database by lazy {
        EatBookRoomDatabase.getDataBase(this)
    }

    val repositoryUser by lazy {
        UserRepositoryImpl(
            database.userDao(),
            applicationContext
        )
    }
    val repositoryRestaurant by lazy {
        RestaurantRepositoryImpl(
            database.restaurantDao(),
            applicationContext
        )
    }
}