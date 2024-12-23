package com.example.plantopiapp.servicios

import com.example.plantopiapp.Constants
import com.example.plantopiapp.dataclases.UsuarioGet
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface UsuarioGetService {

    @GET(Constants.PATH_USUARIOS)
    suspend fun getUsuarioByEmailtAndPass(
        @Query("email") email: String,
        @Query("clave") clave: String
    ): Response<List<UsuarioGet>>

    @POST(Constants.PATH_ADD_USUARIO)
    suspend fun addUsuario(
        @Body usuario: UsuarioGet
    ): Response<UsuarioGet>


}

