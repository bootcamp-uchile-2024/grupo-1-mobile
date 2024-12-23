package com.example.plantopiapp.room_Carrito

import android.content.Context
import androidx.room.Room

object CarritoManager {

    private var carritoDatabase: CarritoDatabase? = null

    fun getCarritoDatabase(context: Context): CarritoDatabase{
        if (carritoDatabase == null){
            carritoDatabase = Room.databaseBuilder(
                context.applicationContext,
                CarritoDatabase::class.java,
                "Carrito.db"
            ).build()

        }
        return carritoDatabase!!

    }

}