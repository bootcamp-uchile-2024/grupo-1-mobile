package com.example.plantopia.openhelper

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class ComprasOpenHelper(context: Context): SQLiteOpenHelper(context, "compras.db", null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE carrito (id INTEGER PRIMARY KEY AUTOINCREMENT, email_compras TEXT, nombre_compras TEXT, apellido_compras TEXT, direccion_compras TEXT, comuna_compras TEXT, region_compras TEXT, telefono_compras TEXT, total_compras TEXT)")
    }
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS compras")
        onCreate(db)
    }
}


