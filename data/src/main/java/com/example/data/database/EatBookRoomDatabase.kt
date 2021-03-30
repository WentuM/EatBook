package com.example.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.data.database.dao.UserDao
import com.example.data.database.entity.User

@Database(entities = arrayOf(User::class), version = 1, exportSchema = false)

abstract class EatBookRoomDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: EatBookRoomDatabase? = null

        fun getDataBase(context: Context): EatBookRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance: EatBookRoomDatabase = Room.databaseBuilder(
                    context.applicationContext,
                    EatBookRoomDatabase::class.java,
                    "eatbook_database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance

                instance
            }
        }
    }
}