package com.example.plantopiapp.clientes

import com.example.plantopiapp.Constants
import com.example.plantopiapp.servicios.UsuarioGetService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object UsuarioGetRetrofitClient {

    val instance: UsuarioGetService by lazy {
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UsuarioGetService::class.java)
    }
}
