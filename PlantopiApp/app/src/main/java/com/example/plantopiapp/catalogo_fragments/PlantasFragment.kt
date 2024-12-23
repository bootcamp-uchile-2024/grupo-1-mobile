package com.example.plantopiapp.catalogo_fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.plantopiapp.R
import com.example.plantopiapp.adapters.PlantAdapter
import com.example.plantopiapp.clientes.PlantasRetrofitClient
import com.example.plantopiapp.dataclases.Carrito
import com.example.plantopiapp.dataclases.Planta
import com.example.plantopiapp.room_Carrito.CarritoDatabase
import com.example.plantopiapp.room_Carrito.CarritoManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PlantasFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PlantAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_plantas, container, false)

        val carritoDb = CarritoManager.getCarritoDatabase(requireContext())

        recyclerView = view.findViewById(R.id.recycler_plantas)

        adapter = PlantAdapter(
            onItemClick = { selectedPlant ->
                navigateToDetailFragment(selectedPlant)
            },
            onCartClick = { selectedPlant ->
                Toast.makeText(requireContext(), "Producto agregado al Carrito", Toast.LENGTH_SHORT).show()
                addToCart(carritoDb, selectedPlant)
            },
            onJardinClick = { selectedPlant ->
                // Toast.makeText(requireContext(), "Producto agregado al Jardín", Toast.LENGTH_SHORT).show()
                // addJardin(jardinDb, selectedPlant)

            })

        recyclerView.adapter = adapter

        loadProducts()

        return view
    }

    private fun loadProducts() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = PlantasRetrofitClient.instance.getPlants()
                val productList = response.data // Extraer la lista de "data"

                requireActivity().runOnUiThread {
                    adapter.setProducts(productList)
                }
            } catch (e: Exception) {
                println("Error: ${e.message}")
            }
        }
    }

    private fun navigateToDetailFragment(selectedPlant: Planta) {

        val listurls: MutableList<String> = mutableListOf()

        for (imagen in selectedPlant.producto.imagenes) {
            listurls.add(imagen.urlImagen)
        }

        val fragment = ProductoFragment().apply {
            arguments = Bundle().apply {
                putString("plantName", selectedPlant.producto.nombreProducto)
                putInt("valoracion", selectedPlant.producto.valoracion)
                putString("plantDescription", selectedPlant.producto.descripcionProducto)
                putInt("plantPrice", selectedPlant.producto.precioNormal)
                putStringArrayList("plantImages", ArrayList(listurls))
                putInt("plantPeso", selectedPlant.peso)
                putInt("plantToxicidad", selectedPlant.toxicidadMascotas)
                putString("plantTemperatura", selectedPlant.temperaturaIdeal)
            }
        }

        parentFragmentManager.beginTransaction()
            .replace(R.id.frame_home, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun addToCart(carritoDb: CarritoDatabase, planta: Planta) {

        val carrito = Carrito(
            planta.nombrePlanta,
            planta.producto.precioNormal,
            1,
            planta.producto.imagenes[0].urlImagen,
        )

        lifecycleScope.launch {
            carritoDb.CarritoDao().insertCarritoItem(carrito)
        }
    }


}

