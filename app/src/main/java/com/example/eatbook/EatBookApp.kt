package com.example.eatbook

import android.app.Application
import com.example.data.database.EatBookRoomDatabase
import com.example.data.di.component.FBComponent
import com.example.eatbook.di.component.AppComponent
import com.example.eatbook.di.component.DaggerAppComponent

class EatBookApp : Application() {
    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder().application(this).build()

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
//        lateinit var fbComponent: FBComponent
    }

    val database by lazy {
        EatBookRoomDatabase.getDataBase(this)
    }

//    val repositoryUser by lazy {
//        UserRepositoryImpl(
//            database.userDao(),
//            applicationContext
//        )
//    }
//    val repositoryRestaurant by lazy {
//        RestaurantRepositoryImpl(
//            database.restaurantDao(),
//            applicationContext
//        )
//    }
}