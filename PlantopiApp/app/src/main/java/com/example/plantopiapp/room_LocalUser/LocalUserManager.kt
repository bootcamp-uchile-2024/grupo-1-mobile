package com.example.plantopiapp.room_LocalUser

import android.content.Context
import androidx.room.Room

object LocalUserManager {

    private var localUserDatabase: LocalUserDatabase? = null

    fun getLocalUserDatabase(context: Context): LocalUserDatabase {
        if (localUserDatabase == null) {
            localUserDatabase = Room.databaseBuilder(
                context.applicationContext,
                LocalUserDatabase::class.java,
                "LocalUser.db"
            ).build()

        }
        return localUserDatabase!!
    }
}