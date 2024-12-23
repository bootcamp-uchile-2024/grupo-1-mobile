package com.example.plantopiapp.dataclases

import androidx.room.Entity
import androidx.room.PrimaryKey

data class Planta(@PrimaryKey val id: Int,
                  val nombrePlanta: String,
                  val nombreCientifico: String,
                  val temperaturaIdeal: String,
                  val toxicidadMascotas: Int,
                  val tamanoMaximo: Int,
                  val peso: Int,
                  val producto: Producto,
                  var isFavorite: Boolean
)