package com.example.plantopiapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.plantopiapp.R
import com.example.plantopiapp.dataclases.Sustrato
import java.text.NumberFormat
import java.util.Locale

class SustratosAdapter : RecyclerView.Adapter<SustratosAdapter.ProductViewHolder>() {
        private val productList = mutableListOf<Sustrato>()

        class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val nameTextView: TextView = itemView.findViewById(R.id.tv_nombre_producto)
            val precioTextView: TextView = itemView.findViewById(R.id.tv_precio_producto)
            val plantaImageView: ImageView = itemView.findViewById(R.id.iv_producto)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_producto, parent, false)
            return ProductViewHolder(view)
        }

        override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {

            val product = productList[position]
            val numberFormat = NumberFormat.getNumberInstance(Locale.GERMANY)
            val formattedPrice = numberFormat.format(product.producto.precioNormal)

            holder.nameTextView.text = product.producto.nombreProducto
            holder.precioTextView.text = "$$formattedPrice"


            // Aquí puedes cargar la imagen utilizando una biblioteca de carga de imágenes como Glide o Picasso
            Glide.with(holder.itemView.context)
                .load(product.producto.imagenes[0].urlImagen)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(holder.plantaImageView)
        }

        override fun getItemCount() = productList.size

        fun setProducts(newProducts: List<Sustrato>) {
            productList.clear()
            productList.addAll(newProducts)
            notifyDataSetChanged()
        }
    }

