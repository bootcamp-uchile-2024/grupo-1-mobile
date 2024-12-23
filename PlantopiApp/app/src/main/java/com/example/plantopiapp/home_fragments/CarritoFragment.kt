package com.example.plantopiapp.home_fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.plantopiapp.Home
import com.example.plantopiapp.R
import com.example.plantopiapp.adapters.CarritoAdapter
import com.example.plantopiapp.carrito_fragments.DatosEnvioFragment
import com.example.plantopiapp.dataclases.Carrito
import com.example.plantopiapp.register_fragments.Register_2nd
import com.example.plantopiapp.room_Carrito.CarritoDatabase
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.Locale

class CarritoFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CarritoAdapter
    private lateinit var carritoDb: CarritoDatabase
    private lateinit var totalCartPriceTextView: TextView
    private lateinit var total_en_productos: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_carrito, container, false)

        // Initialize RecyclerView
        recyclerView = view.findViewById(R.id.recycler_carrito)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Initialize Total Cart Price TextView
        totalCartPriceTextView = view.findViewById(R.id.total_cart_price)
        total_en_productos = view.findViewById<TextView>(R.id.tv_num_subtotal)

        // Initialize Adapter
        adapter = CarritoAdapter(mutableListOf()) { carrito, newQuantity, removeItem ->
            modifyQuantity(carrito, newQuantity, removeItem)
        }
        recyclerView.adapter = adapter

        // Inicio el carro de compras


        // Initialize the Database Local
        carritoDb = Room.databaseBuilder(
            requireContext(),
            CarritoDatabase::class.java,
            "Carrito.db"
        ).build()

        // Fetch Data and Observe Total Price
        fetchData()
        observeTotalPrice()

        // Observe total item count for the badge
        observeCartBadge()


        // Obtener boton para avanzar al siguiente paso
        val button_next = view.findViewById<MaterialButton>(R.id.button_continuar_compra)

        button_next.setOnClickListener {

            val next_fragment = DatosEnvioFragment()
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

    private fun fetchData() {
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val carritoList = carritoDb.CarritoDao().getGroupedProducts()
                if (carritoList.isEmpty()) {
                    recyclerView.visibility = View.GONE
                    view?.findViewById<View>(R.id.empty_view)?.visibility = View.VISIBLE
                } else {
                    recyclerView.visibility = View.VISIBLE
                    view?.findViewById<View>(R.id.empty_view)?.visibility = View.GONE
                    adapter.updateList(carritoList)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun modifyQuantity(carrito: Carrito, newQuantity: Int, removeItem: Boolean) {
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                if (removeItem || newQuantity <= 0) {
                    // Remove the item if the quantity is zero or explicitly requested
                    carritoDb.CarritoDao().deleteCarritoItem(carrito)
                } else {
                    // Add or update the product
                    val updatedProduct = carrito.copy(cantidad = newQuantity)
                    carritoDb.CarritoDao().addOrUpdateCarrito(updatedProduct)
                }

                // Refresh the list
                fetchData()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun observeTotalPrice() {
        carritoDb.CarritoDao().getTotalCartPrice().observe(viewLifecycleOwner) { totalPrice ->

            val numberFormat = NumberFormat.getNumberInstance(Locale.GERMANY)

            if (totalPrice != null) {
                val formattedPrice = numberFormat.format(totalPrice)
                totalCartPriceTextView.text = "$${formattedPrice ?: 0}"
                total_en_productos.text = "$${formattedPrice ?: 0}"
            } else {
                totalCartPriceTextView.text = "$${0}"
                total_en_productos.text = "$${0}"
            }
        }
    }
}

