package com.example.plantopiapp.dataclases

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Carrito")
data class Carrito(
    val nombrePlanta: String,
    val precioPlanta: Int,
    var cantidad: Int,
    var url: String,
    @PrimaryKey(autoGenerate = true) val id: Int = 0
)