package com.example.plantopiapp.catalogo_fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.plantopiapp.R
import com.example.plantopiapp.adapters.MaceterosAdapter
import com.example.plantopiapp.clientes.MaceterosRetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MaceterosFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MaceterosAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_maceteros, container, false)

        recyclerView = view.findViewById(R.id.recycler_maceteros)
        adapter = MaceterosAdapter()
        recyclerView.adapter = adapter

        loadProducts()

        return view
    }

    private fun loadProducts() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = MaceterosRetrofitClient.instance.getMaceteros()
                val productList = response.data // Extraer la lista de "data"

                requireActivity().runOnUiThread {
                    adapter.setProducts(productList)
                }
            } catch (e: Exception) {
                println("Error: ${e.message}")
            }
        }
    }
}

