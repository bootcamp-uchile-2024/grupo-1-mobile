package com.example.plantopiapp.dataclases

data class Fertilizante(val id: Int,
                        val composicion: String,
                        val presentacion: String,
                        val frecuenciaAplicacion: String,
                        val peso: Int,
                        val producto: Producto
)
