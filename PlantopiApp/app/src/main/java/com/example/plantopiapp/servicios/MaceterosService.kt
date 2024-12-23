package com.example.plantopiapp.servicios

import com.example.plantopiapp.Constants
import com.example.plantopiapp.dataclases.MaceterosResponse
import retrofit2.http.GET

interface MaceterosService {
    @GET(Constants.PATH_MACETEROS)
    suspend fun getMaceteros(): MaceterosResponse
}
