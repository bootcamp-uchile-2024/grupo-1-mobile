package com.example.plantopiapp.dataclases

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "LocalUser")
data class LocalUser(
    val rutUsuario: String,
    val nombres: String,
    val apellidos: String,
    val email: String,
    val clave: String,
    val telefono: Int,
    val direccion: String,
    val idComuna: Int,
    val codigoPostal: String,
    val idPerfil: Int,
    val respuesta1: String,
    val respuesta2: String,
    val respuesta3: String,
    val respuesta4: String,
    val respuesta5: String,
    val respuesta6: String,
    val respuesta7: String,
    val respuesta8: String,
    val respuesta9: String,
    @PrimaryKey(autoGenerate = true) val id: Int,
)
