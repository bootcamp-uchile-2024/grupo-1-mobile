package com.example.plantopiapp.room_LocalUser

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.plantopiapp.dataclases.LocalUser

@Dao
interface LocalUserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: LocalUser)

    @Delete
    suspend fun deleteUser(user: LocalUser)

    @Update
    suspend fun updateUser(user: LocalUser)

    @Query("SELECT * FROM LocalUser WHERE id = :userId")
    suspend fun getUserById(userId: Int): LocalUser?

}