package com.example.plantopia.helper

import java.net.HttpURLConnection
import java.net.URL

class webServiceHelper(private val baseURL: String?) {
    private lateinit var connection: HttpURLConnection

    fun getGETRequest(resource: String): String? {
        // Crear o abrir la conexion al servicio
        val url = URL(baseURL + resource)
        connection = url.openConnection() as HttpURLConnection


        // Establecer el metodo de solicitud y realizar la conexion
        connection.requestMethod = "GET"
        connection.connectTimeout = 5000
        connection.readTimeout = 5000
        connection.connect()

        try {
            // leeer la respuesta del servicio
            val responseCode = connection.responseCode
            if (responseCode == HttpURLConnection.HTTP_OK) {
                return connection.inputStream.bufferedReader().readText()
            } else {
                connection.disconnect()
                return null
            }

        } catch (e: Exception) {
            e.printStackTrace()
            connection.disconnect()
            return null
        }
        return null
    }
}

