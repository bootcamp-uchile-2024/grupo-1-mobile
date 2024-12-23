package com.example.plantopiapp.carrito_fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.plantopiapp.Home
import com.example.plantopiapp.R
import com.example.plantopiapp.adapters.CarritoAdapter
import com.example.plantopiapp.catalogo_fragments.ProductoFragment
import com.example.plantopiapp.dataclases.Carrito
import com.example.plantopiapp.room_Carrito.CarritoDatabase
import com.example.plantopiapp.room_LocalUser.LocalUserDatabase
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.NumberFormat
import java.util.Locale

class DatosEnvioFragment : Fragment() {

    private lateinit var carritoDb: CarritoDatabase
    private lateinit var localUserDb: LocalUserDatabase
    private lateinit var totalCartPriceTextView: TextView
    private lateinit var total_en_productos: TextView
    private lateinit var valor_en_envios: TextView
    private lateinit var direccion_plantopper: TextView
    private lateinit var direccion_normal: TextView
    private lateinit var check_plantopia: CheckBox
    private lateinit var check_normal: CheckBox
    private val envioNumericoLiveData = MutableLiveData(0)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_datos_envio, container, false)

        total_en_productos = view.findViewById(R.id.envio_total_productos)
        totalCartPriceTextView = view.findViewById(R.id.envio_suma)

        check_plantopia = view.findViewById(R.id.checkbox_envio_plantoper)
        check_normal = view.findViewById(R.id.checkbox_envio_normal)
        valor_en_envios = view.findViewById(R.id.tv_valor_envio)
        valor_en_envios.text = "$0"

        var envio_numerico = 0

        // Asegura que solo uno esté presionado
        check_plantopia.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                check_normal.isChecked = false // Uncheck the other checkbox
                valor_en_envios.text = "$0"
                envioNumericoLiveData.value = 0
            }
        }

        check_normal.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                check_plantopia.isChecked = false // Uncheck the other checkbox
                valor_en_envios.text = "$2.990"
                envioNumericoLiveData.value = 2990
            }
        }


        // Initialize the Database
        carritoDb = Room.databaseBuilder(
            requireContext(),
            CarritoDatabase::class.java,
            "Carrito.db"
        ).build()

        // Observe Total Price
        observeTotalPrice()

        // Observe total item count for the badge
        observeCartBadge()

        // Consultar Direccion del usuario
        // Inicializar la base de datos de usuarios locales
        localUserDb = Room.databaseBuilder(
            requireContext(),
            LocalUserDatabase::class.java,
            "LocalUser.db"
        ).build()

        direccion_normal = view.findViewById(R.id.tv_envioNormal_direccion)
        direccion_plantopper = view.findViewById(R.id.tv_envioPlantoper_direccion)

        // Consultar y mostrar el nombre del usuario
        lifecycleScope.launch {
            val user = withContext(Dispatchers.IO) {
                localUserDb.localUserDao().getUserById(1)
            }

            withContext(Dispatchers.Main) {
                if (user != null) {
                    direccion_normal.text = user.direccion.toString()
                    direccion_plantopper.text = user.direccion.toString()
                } else {

                    Log.d("HomeFragment", "Usuario con ID 1 no existe en la base de datos")
                }
            }
        }

        // Ir a la siguiente etapa
        // Obtener boton para avanzar al siguiente paso
        val button_next = view.findViewById<MaterialButton>(R.id.envios_continuar_compra)

        button_next.setOnClickListener {

            val next_fragment = DatosPagoFragment()

            next_fragment.apply {
                arguments = Bundle().apply {
                    putString("costo_plantas", total_en_productos.text.toString())
                    putString("costo_envio", valor_en_envios.text.toString())
                    putString("costo_total", totalCartPriceTextView.text.toString())
                }
            }

            parentFragmentManager.beginTransaction()
                .replace(R.id.frame_home, next_fragment)
                .addToBackStack(null) // Optional: Adds transaction to back stack
                .commit()

        }


        return view
    }

    private fun observeCartBadge() {
        carritoDb.CarritoDao().getTotalItemCount().observe(viewLifecycleOwner) { totalCount ->
            (requireActivity() as? Home)?.updateCartBadge(totalCount ?: 0)
        }
    }


    private fun observeTotalPrice() {
        envioNumericoLiveData.observe(viewLifecycleOwner) { envioNumerico ->
            carritoDb.CarritoDao().getTotalCartPrice().observe(viewLifecycleOwner) { subtotal ->
                // Handle null subtotal and calculate the total price
                val subtotalValue = subtotal?.toInt() ?: 0
                val totalConEnvio = subtotalValue + envioNumerico

                // Format the prices
                val numberFormat = NumberFormat.getNumberInstance(Locale.GERMANY)
                val formattedSubtotal = numberFormat.format(subtotalValue)
                val formattedTotal = numberFormat.format(totalConEnvio)

                // Update the UI
                totalCartPriceTextView.text = "$${formattedTotal}"
                total_en_productos.text = "$${formattedSubtotal}"
            }
        }
    }



}