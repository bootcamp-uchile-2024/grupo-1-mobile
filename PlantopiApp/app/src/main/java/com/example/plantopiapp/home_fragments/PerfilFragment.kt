package com.example.plantopiapp.home_fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.plantopiapp.Home
import com.example.plantopiapp.R
import com.example.plantopiapp.R.id.tv_in_nombre
import com.example.plantopiapp.room_Carrito.CarritoDatabase
import com.example.plantopiapp.room_LocalUser.LocalUserDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class PerfilFragment : Fragment() {

    private lateinit var localUserDb: LocalUserDatabase

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_perfil, container, false)

        // Verificar si hay sesión iniciada
        val iniSesion = arguments?.getString("iniSesion")
        Log.d("HomeFragment", "Valor de iniSesion: $iniSesion")

        val nombre = view.findViewById<TextView>(tv_in_nombre)
        val apellido = view.findViewById<TextView>(R.id.tv_in_apellido)
        val email = view.findViewById<TextView>(R.id.tv_in_correo)
        val telefono = view.findViewById<TextView>(R.id.tv_in_telefono)
        val direccion = view.findViewById<TextView>(R.id.tv_in_direccion)
        val codigoPostal = view.findViewById<TextView>(R.id.tv_in_codigoPostal)
        val preferencia1 = view.findViewById<TextView>(R.id.tv_preferencia1)
        val preferencia2 = view.findViewById<TextView>(R.id.tv_preferencia2)
        val preferencia3 = view.findViewById<TextView>(R.id.tv_preferencia3)
        val preferencia4 = view.findViewById<TextView>(R.id.tv_preferencia4)
        val preferencia5 = view.findViewById<TextView>(R.id.tv_preferencia5)

        if (iniSesion == "true") {
            // Inicializar la base de datos de usuarios locales
            localUserDb = Room.databaseBuilder(
                requireContext(),
                LocalUserDatabase::class.java,
                "LocalUser.db"
            ).build()

            // Consultar y mostrar el nombre del usuario
            lifecycleScope.launch {
                val user = withContext(Dispatchers.IO) {
                    localUserDb.localUserDao().getUserById(1)
                }

                withContext(Dispatchers.Main) {
                    if (user != null) {
                        nombre.text = user.nombres
                        apellido.text = user.apellidos
                        email.text = user.email
                        telefono.text = user.telefono.toString()
                        direccion.text = user.direccion
                        codigoPostal.text = user.codigoPostal
                        preferencia1.text = user.respuesta1
                        preferencia2.text = user.respuesta2
                        preferencia3.text = user.respuesta3
                        preferencia4.text = user.respuesta4
                        preferencia5.text = user.respuesta5

                    } else {

                        Log.d("HomeFragment", "Usuario con ID 1 no existe en la base de datos")
                    }
                }
            }
        } else {
            Toast.makeText(requireContext(), "Inicia sesión para ver esta información", Toast.LENGTH_SHORT).show()
        }

        return view
    }

}