package com.example.plantopiapp.clientes

import com.example.plantopiapp.servicios.PlantasService
import com.example.plantopiapp.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object PlantasRetrofitClient {

    val instance: PlantasService by lazy {
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PlantasService::class.java)
    }
}
