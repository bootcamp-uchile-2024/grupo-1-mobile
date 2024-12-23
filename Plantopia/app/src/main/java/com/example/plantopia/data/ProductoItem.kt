package com.example.plantopia.data

data class ProductoItem (val nombreProducto: String,
                         val descuento: Int,
                         val precioNormal: Int,
                         val stock: Int,
                         val descripcion: String,
                         val url: String,
                         val valoracion: Double,
                         val cantidadVentas: Int,
                         val imagenes: List<String>)
