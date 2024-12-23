package com.example.plantopiapp.clientes

import com.example.plantopiapp.servicios.SustratosService
import com.example.plantopiapp.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object SustratosRetrofitClient {

    val instance: SustratosService by lazy {
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(SustratosService::class.java)
    }
}
