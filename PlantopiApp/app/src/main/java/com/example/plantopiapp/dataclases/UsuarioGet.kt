package com.example.plantopiapp.dataclases

data class UsuarioGet(
    val rutUsuario: String,
    val nombres: String,
    val apellidos: String,
    val email: String,
    val clave: String,
    val telefono: Int,
    val direccion: String,
    val idComuna: Int,
    val codigoPostal: String,
    val idPerfil: Int = 2,
    val Preferencias: List<Preferencias>
)