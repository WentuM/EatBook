package com.example.eatbook

import android.app.Application
import android.content.Context
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
        lateinit var context: Context
    }

    val database by lazy {
        EatBookRoomDatabase.getDataBase(this)
    }
}