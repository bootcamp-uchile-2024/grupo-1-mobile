package com.example.plantopiapp.servicios

import com.example.plantopiapp.Constants
import com.example.plantopiapp.dataclases.FertilizanteResponse
import retrofit2.http.GET

interface FertilizanteService {
    @GET(Constants.PATH_FERTILIZANTES)
    suspend fun getFertilizantes(): FertilizanteResponse
}
