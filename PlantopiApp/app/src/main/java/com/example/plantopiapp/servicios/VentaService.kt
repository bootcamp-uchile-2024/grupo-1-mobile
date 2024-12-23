package com.example.plantopiapp.servicios

import com.example.plantopiapp.dataclases.Venta
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface VentaService {

    @POST("ventas/carrito/")
    suspend fun getVenta(emailComprador: String, idUsuario: Int): Response<Venta>
}