package com.example.plantopiapp.home_fragments

import Catalogo
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.plantopiapp.BadgeViewModel
import com.example.plantopiapp.Home
import com.example.plantopiapp.R
import com.example.plantopiapp.catalogo_fragments.ProductoFragment
import com.example.plantopiapp.recoverPass_fragments.RecuperaPass_2nd
import com.example.plantopiapp.register_fragments.Register_2nd
import com.example.plantopiapp.room_Carrito.CarritoDatabase
import com.example.plantopiapp.room_LocalUser.LocalUserDatabase
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeFragment : Fragment() {

    private lateinit var carritoDb: CarritoDatabase
    private lateinit var localUserDb: LocalUserDatabase

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val texto = view.findViewById<TextView>(R.id.textView2)
        val button = view.findViewById<Button>(R.id.buttontoCatalogo)

        button.setOnClickListener {
            deleteDatabase()
        }

        // Inicializar la base de datos del carrito
        carritoDb = Room.databaseBuilder(
            requireContext(),
            CarritoDatabase::class.java,
            "Carrito.db"
        ).build()

        // Actualizar el badge del carrito
        observeCartBadge()

        // Verificar si hay sesión iniciada
        val iniSesion = arguments?.getString("iniSesion")
        Log.d("HomeFragment", "Valor de iniSesion: $iniSesion")

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
                        val nombre = user.nombres
                        Toast.makeText(requireContext(), "Bienvenido $nombre", Toast.LENGTH_SHORT).show()
                        texto.text = "Bienvenido $nombre"
                    } else {
                        texto.text = "Usuario no encontrado"
                        Log.d("HomeFragment", "Usuario con ID 1 no existe en la base de datos")
                    }
                }
            }
        } else {
            texto.text = "Inicia sesión para continuar"
        }



        return view
    }

    private fun observeCartBadge() {
        carritoDb.CarritoDao().getTotalItemCount().observe(viewLifecycleOwner) { totalCount ->
            (requireActivity() as? Home)?.updateCartBadge(totalCount ?: 0)
        }
    }

    private fun deleteDatabase() {
        val dbName = "Carrito.db"
        val dbFile = requireContext().getDatabasePath(dbName)
        if (dbFile.exists()) {
            val deleted = dbFile.delete()
            if (deleted) {
                Log.d("HomeFragment", "Base de datos eliminada exitosamente")
            } else {
                Log.d("HomeFragment", "No se pudo eliminar la base de datos")
            }
        } else {
            Log.d("HomeFragment", "Archivo de base de datos no encontrado")
        }
    }
}


