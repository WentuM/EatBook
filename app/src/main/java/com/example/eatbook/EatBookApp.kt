package com.example.eatbook

import android.app.Application
import com.example.data.database.EatBookRoomDatabase

class EatBookApp : Application() {

    val database by lazy {
        EatBookRoomDatabase.getDataBase(this)
    }

//    val repository by lazy {
//        CharacterRepositoryImpl(
//            ApiFactory.characterApi,
//            database.characterDao(),
//            applicationContext
//        )
//    }
}