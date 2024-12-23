package com.example.plantopiapp.servicios

import com.example.plantopiapp.Constants
import com.example.plantopiapp.dataclases.PlantaResponse
import retrofit2.http.GET

interface PlantasService {
    @GET(Constants.PATH_PLANTAS)
    suspend fun getPlants(): PlantaResponse
}
