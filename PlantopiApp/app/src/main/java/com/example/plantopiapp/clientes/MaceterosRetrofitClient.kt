package com.example.plantopiapp.clientes

import com.example.plantopiapp.servicios.MaceterosService
import com.example.plantopiapp.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MaceterosRetrofitClient {

    val instance: MaceterosService by lazy {
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MaceterosService::class.java)
    }
}
