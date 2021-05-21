package com.example.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.data.database.dao.*
import com.example.data.database.entity.*

@Database(
    entities = arrayOf(
        UserEntity::class,
        RestaurantEntity::class,
        SaleEntity::class,
        TableEntity::class,
        ReviewEntity::class,
        BookTableEntity::class,
        FavouriteRestEntity::class
    ), version = 9, exportSchema = false
)

abstract class EatBookRoomDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun restaurantDao(): RestaurantDao
    abstract fun saleDao(): SaleDao
    abstract fun tableDao(): TableDao
    abstract fun reviewDao(): ReviewDao
    abstract fun bookTableDao(): BookTableDao
    abstract fun favouriteRestDao(): FavouriteRestDao

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