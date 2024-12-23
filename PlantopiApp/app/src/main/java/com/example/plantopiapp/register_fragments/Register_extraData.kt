package com.example.plantopiapp.register_fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.plantopiapp.Constants
import com.example.plantopiapp.Home
import com.example.plantopiapp.MainActivity
import com.example.plantopiapp.R
import com.example.plantopiapp.clases.ProgressBarController
import com.example.plantopiapp.dataclases.Preferencias
import com.example.plantopiapp.dataclases.Usuario
import com.example.plantopiapp.servicios.UsuarioService
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Register_extraData : Fragment(R.layout.fragment_register_extra_data) {

    private var progressBarController: ProgressBarController? = null
    private var progress = 60

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ProgressBarController) {
            progressBarController = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        progressBarController = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val in_mail = arguments?.getString("mail")
        val in_name = arguments?.getString("name")
        val in_lastname = arguments?.getString("lastname")
        val in_password = arguments?.getString("password")

        val in_run = view.findViewById<EditText>(R.id.editText_rut)
        val in_telefono = view.findViewById<EditText>(R.id.editText_telefono)
        val in_direccion = view.findViewById<EditText>(R.id.editText_direccion)
        val in_comuna = view.findViewById<EditText>(R.id.editText_comuna)
        val in_codigo_postal = view.findViewById<EditText>(R.id.editText_codigo_postal)

        // Agrego a la base de datos
        simulateProgress()

        val button_no_info = view.findViewById<Button>(R.id.button_sin_info)
        button_no_info.setOnClickListener {

            lifecycleScope.launch {
                val nuevo_usuario = Usuario(
                    rutUsuario = "",
                    nombres = in_name.toString(),
                    apellidos = in_lastname.toString(),
                    email = in_mail.toString(),
                    clave = in_password.toString(),
                    telefono = 0,
                    direccion = "",
                    idComuna = 1,
                    idPerfil = 1,
                    codigoPostal = "",
                    respuesta1 = " ",
                    respuesta2 = " ",
                    respuesta3 = " ",
                    respuesta4 = " ",
                    respuesta5 = " ",
                    respuesta6 = " ",
                    respuesta7 = " ",
                    respuesta8 = " ",
                    respuesta9 = " "
                )

                val usuarioCreado = registrarUsuario(nuevo_usuario)

                if (usuarioCreado != null) {
                    Toast.makeText(
                        context,
                        "Usuario creado: ${usuarioCreado.nombres}",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(context, "Error al crear usuario", Toast.LENGTH_SHORT).show()
                }
            }

            // Se completa registro y se va al home
            val intent = Intent(context, Home::class.java)
            startActivity(intent)
        }


        // Cuando el usuario quiere continuar con las preguntas
        val button_add_info = view.findViewById<Button>(R.id.button_register_adicional)
        button_add_info.setOnClickListener {

            val next_fragment = Register_3rd()

            next_fragment.apply {
                arguments = Bundle().apply {
                    putString("mail", in_mail)
                    putString("name", in_name)
                    putString("lastname", in_lastname)
                    putString("password", in_password)
                    putString("run", in_run.text.toString())
                    putString("telefono", in_telefono.text.toString())
                    putString("direccion", in_direccion.text.toString())
                    putString("comuna", in_comuna.text.toString())
                    putString("codigo_postal", in_codigo_postal.text.toString())
                }
            }

            parentFragmentManager.beginTransaction()
                .replace(R.id.frame_layout_register, next_fragment)
                .addToBackStack(null) // Optional: Adds transaction to back stack
                .commit()
        }
    }

    private fun getRetrofit(): Retrofit {
           return Retrofit.Builder()
               .baseUrl(Constants.BASE_URL)
               .addConverterFactory(GsonConverterFactory.create())
               .build()
    }

    suspend fun registrarUsuario(usuario: Usuario): Usuario? {
           val response = getRetrofit().create(UsuarioService::class.java).addUsuario(usuario)
           if (response.isSuccessful) {
               return response.body() // Return the created Usuario object
           } else {
               return null // Handle the error case
           }
    }

    private fun simulateProgress() {
        var currentProgress = progressBarController?.getProgress() ?: 0
        currentProgress = progress

        activity?.runOnUiThread {
            progressBarController?.updateProgress(currentProgress)
        }
    }

}