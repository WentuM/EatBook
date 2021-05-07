package com.example.eatbook

import android.app.Application
import com.example.data.database.EatBookRoomDatabase
import com.example.eatbook.di.component.AppComponent
import com.example.eatbook.di.component.DaggerAppComponent

class EatBookApp : Application() {
    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder().application(this).build()
    }

    companion object {
        lateinit var appComponent: AppComponent
    }

    val database by lazy {
        EatBookRoomDatabase.getDataBase(this)
    }
}