package com.example.eatbook

import android.app.Application
import com.example.data.database.EatBookRoomDatabase
import com.example.data.di.component.FBComponent
import com.example.data.repository.RestaurantRepositoryImpl
import com.example.data.repository.UserRepositoryImpl
import com.example.eatbook.di.component.AppComponent

class EatBookApp : Application() {

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder().application(this).build()
//        fbComponent = DaggerFBComponent.builder().fbModule(FireBaseModule(this)).build()

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
        lateinit var fbComponent: FBComponent
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