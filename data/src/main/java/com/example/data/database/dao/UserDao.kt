package com.example.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.database.entity.UserEntity

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(userEntity: UserEntity)

    @Query("SELECT * FROM user WHERE id = :idUser")
    suspend fun getUserById(idUser: String): UserEntity

    @Query("UPDATE user SET username = :name WHERE id =:id")
    suspend fun updateUser(name: String, id: String)
}