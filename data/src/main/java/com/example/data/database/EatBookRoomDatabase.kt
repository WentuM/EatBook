package com.example.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.data.database.dao.RestaurantDao
import com.example.data.database.dao.SaleDao
import com.example.data.database.dao.UserDao
import com.example.data.database.entity.RestaurantEntity
import com.example.data.database.entity.UserEntity

@Database(entities = arrayOf(UserEntity::class, RestaurantEntity::class), version = 3, exportSchema = false)

abstract class EatBookRoomDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun restaurantDao(): RestaurantDao
    abstract fun saleDao(): SaleDao

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