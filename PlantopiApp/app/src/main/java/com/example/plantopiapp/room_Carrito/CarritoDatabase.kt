package com.example.plantopiapp.room_Carrito

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.plantopiapp.dataclases.Carrito
import com.example.plantopiapp.room_Jardin.CarritoDao

@Database(entities = [Carrito::class], version = 2, exportSchema = true)
abstract class CarritoDatabase: RoomDatabase() {
    abstract fun CarritoDao(): CarritoDao
}