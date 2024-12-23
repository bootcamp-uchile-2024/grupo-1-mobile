package com.example.plantopiapp.dataclases

import java.time.LocalDateTime

data class JardinVirtual(
    val id: Int,
    var nombrePlanta: String,
    val nombreComun: String,
    val imagen: String,
    val frecuencia_de_riego: Int,
    var proximo_riego: LocalDateTime
)
