package com.example.plantopiapp.ui.fragments

import JardinVirtualAdapter
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.plantopiapp.R
import com.example.plantopiapp.dataclases.JardinVirtual
import java.time.LocalDateTime

// Fragmento para mostrar el jardín virtual de plantas
class MiJardinFragment : Fragment() {

    // Declaración del RecyclerView y el adaptador
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: JardinVirtualAdapter

    // Infla el diseño del fragmento cuando se crea
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Infla el archivo de diseño correspondiente
        return inflater.inflate(R.layout.fragment_mi_jardin, container, false)
    }

    // Configura el RecyclerView y los datos después de que la vista ha sido creada
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inicializa el RecyclerView
        recyclerView = view.findViewById(R.id.recyclerView)

        // Datos de ejemplo para mostrar en el RecyclerView
        val samplePlants = listOf(
            JardinVirtual(1, "Aloe Vera", "Sábila", "https", 3, LocalDateTime.now().plusDays(3)),
            JardinVirtual(2, "Rose", "Rosa", "http", 7, LocalDateTime.now().plusDays(7))
        )

        // Configura el adaptador con los datos de ejemplo
        adapter = JardinVirtualAdapter(samplePlants, requireContext())
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        // Programa recordatorios para todas las plantas en la lista
        samplePlants.forEach { plant ->
            scheduleNotification(requireContext(), plant)
        }
    }

    // Programa un recordatorio para una planta específica usando WorkManager
    private fun scheduleNotification(context: Context, plant: JardinVirtual) {
        // Aquí va la lógica para programar recordatorios usando WorkManager
        // Ejemplo:
        // WorkManager.getInstance(context).enqueue(...)
    }
}
