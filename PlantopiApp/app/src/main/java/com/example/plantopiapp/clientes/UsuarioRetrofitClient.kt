package com.example.plantopiapp.clientes

import com.example.plantopiapp.servicios.UsuarioService
import com.example.plantopiapp.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object UsuarioRetrofitClient {

    val instance: UsuarioService by lazy {
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UsuarioService::class.java)
    }
}
