package com.example.plantopiapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.plantopiapp.R
import com.example.plantopiapp.dataclases.Planta
import java.text.NumberFormat
import java.util.Locale
import jp.wasabeef.glide.transformations.RoundedCornersTransformation

class PlantAdapter(
    private var productList: List<Planta> = emptyList(),
    private var onItemClick: (Planta) -> Unit = {},
    private var onCartClick: (Planta) -> Unit = {},
    private var onJardinClick: (Planta) -> Unit = {}
) : RecyclerView.Adapter<PlantAdapter.ProductViewHolder>() {

    private var selectedItemPosition: Int = RecyclerView.NO_POSITION

    class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardView: com.google.android.material.card.MaterialCardView =
            itemView as com.google.android.material.card.MaterialCardView
        val nameTextView: TextView = itemView.findViewById(R.id.tv_nombre_producto)
        val precioTextView: TextView = itemView.findViewById(R.id.tv_precio_producto)
        val plantaImageView: ImageView = itemView.findViewById(R.id.iv_producto)
        val addToCart: ImageView = itemView.findViewById(R.id.iv_add_cart)
        val addToJardin: ImageView = itemView.findViewById(R.id.iv_favorite)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_producto, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = productList[position]
        val numberFormat = NumberFormat.getNumberInstance(Locale.GERMANY)
        val formattedPrice = numberFormat.format(product.producto.precioNormal)

        holder.nameTextView.text = product.producto.nombreProducto
        holder.precioTextView.text = "$$formattedPrice"

        Glide.with(holder.itemView.context)
            .load(product.producto.imagenes[0].urlImagen)
            .placeholder(R.drawable.img1)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .centerCrop()
            .error(R.drawable.img)
            .transform(
                RoundedCornersTransformation(
                    16, // Corner radius in pixels
                    0,  // Margin in pixels
                    RoundedCornersTransformation.CornerType.TOP_RIGHT // Specify the corner type
                )
            )
            .into(holder.plantaImageView)

        // Highlight the selected card
        if (position == selectedItemPosition) {
            holder.cardView.strokeColor =
                ContextCompat.getColor(holder.itemView.context, R.color.Buttons_Secundary_Background_secundary_hover_butt_light) // Highlight color
            holder.cardView.strokeWidth = 4 // Thicker stroke for selected item
        } else {
            holder.cardView.strokeColor =
                ContextCompat.getColor(holder.itemView.context, android.R.color.transparent) // Default
            holder.cardView.strokeWidth = 2 // Normal stroke
        }

        holder.itemView.setOnClickListener {
            val previousPosition = selectedItemPosition
            selectedItemPosition = position

            // Notify changes to update the UI
            notifyItemChanged(previousPosition)
            notifyItemChanged(selectedItemPosition)

            onItemClick(product)
        }

        holder.addToCart.setOnClickListener {
            onCartClick(product)
        }

        holder.addToJardin.setImageResource(
            if (product.isFavorite) R.drawable.ic_potted_plant else R.drawable.ic_no_favorite_with_background
        )

        holder.addToJardin.setOnClickListener {
            product.isFavorite = !product.isFavorite
            notifyItemChanged(position)
            onJardinClick(product)
        }
    }

    override fun getItemCount() = productList.size

    fun setProducts(newProducts: List<Planta>) {
        this.productList = newProducts
        notifyDataSetChanged()
    }
}


