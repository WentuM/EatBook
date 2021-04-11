package com.example.eatbook

import android.app.Application
import com.example.data.database.EatBookRoomDatabase
import com.example.data.di.component.AppComponent
import com.example.data.di.component.DaggerAppComponent
import com.example.data.di.module.AppModule
import com.example.data.repository.RestaurantRepositoryImpl
import com.example.data.repository.UserRepositoryImpl
import com.example.domain.interfaces.RestaurantRepository

class EatBookApp : Application() {

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder().appModule(AppModule(this)).build()
//        appComponent = DaggerAppComponent.builder()
//            .appModule(AppModule(this))
//            .netModule(NetModule())
//            .serviceModule(ServiceModule())
//            .build()
//
//        mainComponent = DaggerMainComponent.builder()
//            .appComponent(appComponent)
//            .weatherModule(WeatherModule())
//            .build()
    }

    companion object {
        lateinit var appComponent: AppComponent
    }

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