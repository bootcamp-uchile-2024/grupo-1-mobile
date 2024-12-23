package com.example.plantopiapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.plantopiapp.R
import com.example.plantopiapp.dataclases.Opiniones
import com.example.plantopiapp.dataclases.Planta
import java.text.NumberFormat
import java.util.Locale

class OpinionAdapter() : RecyclerView.Adapter<OpinionAdapter.OpinionViewHolder>() {
    private val opinionesList = mutableListOf<Opiniones>()

        class OpinionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val stars: RatingBar = itemView.findViewById(R.id.ratingBar_opiniones)
            val titulo: TextView = itemView.findViewById(R.id.tv_opinion_tittle)
            val descripcion: TextView = itemView.findViewById(R.id.tv_opinion_descrip)
            val usuario: TextView = itemView.findViewById(R.id.tv_persona_opinion)
            val fecha: TextView = itemView.findViewById(R.id.tv_fecha_opinion)

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OpinionViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_opinion, parent, false)
            return OpinionViewHolder(view)
        }

        override fun onBindViewHolder(holder: OpinionViewHolder, position: Int) {

            val opiniones = opinionesList[position]

            holder.stars.rating = opiniones.stars.toFloat()
            holder.titulo.text = opiniones.titulo
            holder.descripcion.text = opiniones.descripcion
            holder.usuario.text = opiniones.usuario
            holder.fecha.text = opiniones.fecha

        }

        override fun getItemCount() = opinionesList.size

        fun setOpiniones(newOpiniones: List<Opiniones>) {
            opinionesList.clear()
            opinionesList.addAll(newOpiniones)
            notifyDataSetChanged()
        }
    }

