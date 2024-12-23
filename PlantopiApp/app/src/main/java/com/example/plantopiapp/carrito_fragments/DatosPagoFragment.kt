package com.example.plantopiapp.carrito_fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.plantopiapp.R
import com.example.plantopiapp.dataclases.Usuario
import com.example.plantopiapp.room_Carrito.CarritoDatabase
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.launch
import org.w3c.dom.Text

class DatosPagoFragment : Fragment() {

    private lateinit var totalCartPriceTextView: TextView
    private lateinit var total_en_productos: TextView
    private lateinit var envio_en_productos: TextView
    private lateinit var finalizar_compra: MaterialButton
    private lateinit var carritoDb: CarritoDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_datos_pago, container, false)

        val in_productos = arguments?.getString("costo_plantas")
        val in_envio = arguments?.getString("costo_envio")
        val in_total = arguments?.getString("costo_total")

        total_en_productos = view.findViewById(R.id.pago_total_productos)
        envio_en_productos = view.findViewById(R.id.pago_valor_envio)
        totalCartPriceTextView = view.findViewById(R.id.pago_suma)
        total_en_productos.text = in_productos
        envio_en_productos.text = in_envio
        totalCartPriceTextView.text = in_total

        // Inicio venta en la api

        // Obtener productos del carrito para enviar a la API
        carritoDb = Room.databaseBuilder(
            requireContext(),
            CarritoDatabase::class.java,
            "Carrito.db"
        ).build()

        finalizar_compra = view.findViewById(R.id.pagos_boton_compra)

        finalizar_compra.setOnClickListener {

//            // Enviar carrito a la API
//            lifecycleScope.launch {
//                val nueva_venta = Venta(
//
//                )
//
//                val usuarioCreado = registrarventa(nueva_venta)
//
//                if (usuarioCreado != null) {
//                    Toast.makeText(context, "Venta finalizada",Toast.LENGTH_SHORT).show()
//                    goToUser()
//                } else {
//                    Toast.makeText(context, "Error al crear venta", Toast.LENGTH_SHORT).show()
//                }
//            }

            val next_fragment = CompraFinalizadaFragment()
            parentFragmentManager.beginTransaction()
                .replace(R.id.frame_home, next_fragment)
                .addToBackStack(null) // Optional: Adds transaction to back stack
                .commit()

        }

        return view






    }

}