package com.example.plantopiapp.servicios

import com.example.plantopiapp.Constants
import com.example.plantopiapp.dataclases.SustratoResponse
import retrofit2.http.GET

interface SustratosService {
    @GET(Constants.PATH_SUSTRATOS)
    suspend fun getSustratos(): SustratoResponse
}
