package com.example.plantopiapp.dataclases

data class Maceteros(val id: Int,
                     val material: String,
                     val altura: Int,
                     val ancho: Int,
                     val color: String,
                     val peso: Int,
                     val producto: Producto
)