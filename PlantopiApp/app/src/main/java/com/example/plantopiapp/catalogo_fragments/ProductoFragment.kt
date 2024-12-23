package com.example.plantopiapp.catalogo_fragments

import ChildItem
import ParentItem
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.plantopiapp.R
import com.example.plantopiapp.adapters.ExpandableAdapter
import com.example.plantopiapp.adapters.OpinionAdapter
import com.example.plantopiapp.adapters.ViewPagerAdapter
import com.example.plantopiapp.dataclases.Opiniones
import java.text.NumberFormat
import java.util.Locale

class ProductoFragment : Fragment() {

    private lateinit var adapter: ViewPagerAdapter
    private lateinit var adapter_adapter: OpinionAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_producto, container, false)

        val plantName = arguments?.getString("plantName")
        val plantRating = arguments?.getInt("valoracion")
        val plantDescription = arguments?.getString("plantDescription")
        val plantPrice = arguments?.getInt("plantPrice")
        val plantImageList = arguments?.getStringArrayList("plantImages")
        val plantPeso = arguments?.getInt("plantPeso")
        val plantToxicidad = arguments?.getInt("plantToxicidad")
        var plantTemperatura = arguments?.getString("plantTemperatura")

        val plantNameTextView = view.findViewById<TextView>(R.id.tv_producto)
        plantNameTextView.text = plantName

        val plantRatingBar = view.findViewById<RatingBar>(R.id.ratingBar)
        if (plantRating != null) {
            plantRatingBar.rating = plantRating.toFloat()
        }

        val plantDescriptionTextView = view.findViewById<TextView>(R.id.tv_descripcion)
        plantDescriptionTextView.text = plantDescription

        val plantPriceTextView = view.findViewById<TextView>(R.id.tv_producto_precio)


        val numberFormat = NumberFormat.getNumberInstance(Locale.GERMANY)
        val formattedPrice = numberFormat.format(plantPrice)
        plantPriceTextView.text = "$$formattedPrice"


        // View Pager configuration
        val viewPager = view.findViewById<ViewPager2>(R.id.view_pager_productos)
        if (plantImageList != null) {
            adapter = ViewPagerAdapter(plantImageList.toList())
        }

        viewPager.adapter = adapter

        // Efectos visuales view pager
        viewPager.beginFakeDrag()
        viewPager.fakeDragBy(-10f)
        viewPager.endFakeDrag()

        // Expandable list configuration

        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView_expandable)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        plantTemperatura = "Temperatura Ideal:\n$plantTemperatura °C"

        val parentList = listOf(
            ParentItem(
                "Recomendaciones específicas",
                R.drawable.ic_plus,
                listOf(
                    ChildItem("Nivel de Cuidado:", R.drawable.ic_cuidado_bajo),
                    ChildItem("Iluminación:", R.drawable.ic_light_sunny),
                    ChildItem("Riego:", R.drawable.ic_riego_medio),
                    ChildItem(plantTemperatura, R.drawable.ic_thermometer),
                    ChildItem(plantToxicidad.toString(), R.drawable.ic_pets)
                )
            )
        )

        // Set adapter of the expandable list
        val adapter = ExpandableAdapter(parentList)
        recyclerView.adapter = adapter

        // Opiniones del producto configuracion
        val recyclerViewOpiniones: RecyclerView = view.findViewById(R.id.recycler_opiniones)
        recyclerViewOpiniones.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        var opiniones = listOf(Opiniones(4, "Hermosa", "Fácil de cuidar", "Bot", "10/10/2023"),
            Opiniones(5, "Me encantó", "Perfecta para mi hogar", "Bot", "10/10/2023"))

        val opinionesAdapter = OpinionAdapter()
        opinionesAdapter.setOpiniones(opiniones)
        recyclerViewOpiniones.adapter = opinionesAdapter


        return view
    }

    fun getIntFromImageUrl(url: String): Int {
        return url.hashCode() // Generates a unique Int for the URL
    }

}

