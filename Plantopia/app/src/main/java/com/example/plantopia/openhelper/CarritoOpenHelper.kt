package com.example.plantopia.openhelper

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class CarritoOpenHelper(context: Context): SQLiteOpenHelper(context, "carrito1.db", null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE carrito (id INTEGER PRIMARY KEY AUTOINCREMENT, producto TEXT, precio TEXT)")
    }
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS carrito")
        onCreate(db)
    }
}