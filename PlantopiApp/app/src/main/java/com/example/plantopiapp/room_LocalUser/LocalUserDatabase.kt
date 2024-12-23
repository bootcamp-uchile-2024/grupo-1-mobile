package com.example.plantopiapp.room_LocalUser

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.plantopiapp.dataclases.LocalUser

@Database(entities = [LocalUser::class], version = 1)
abstract class LocalUserDatabase: RoomDatabase() {
    abstract fun localUserDao(): LocalUserDao
}