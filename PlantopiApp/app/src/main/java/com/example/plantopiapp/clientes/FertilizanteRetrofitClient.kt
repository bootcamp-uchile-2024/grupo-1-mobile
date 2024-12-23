package com.example.plantopiapp.clientes

import com.example.plantopiapp.servicios.FertilizanteService
import com.example.plantopiapp.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object FertilizanteRetrofitClient {

    val instance: FertilizanteService by lazy {
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(FertilizanteService::class.java)
    }
}
