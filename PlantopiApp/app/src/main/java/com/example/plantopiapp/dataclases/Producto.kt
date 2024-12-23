package com.example.plantopiapp.dataclases

data class Producto(val id: Int,
                    val nombreProducto: String,
                    val descuento: Int,
                    val precioNormal: Int,
                    val stock: Int,
                    val descripcionProducto: String,
                    val valoracion: Int,
                    val activo: Int,
                    val categoria: Categoria,
                    val imagenes: List<Imagenes>)
