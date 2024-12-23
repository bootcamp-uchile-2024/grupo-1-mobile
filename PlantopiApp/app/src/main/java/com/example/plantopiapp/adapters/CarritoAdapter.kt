package com.example.plantopiapp.adapters

import CarritoDiffCallback
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.RoundedCorner
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.plantopiapp.R
import com.example.plantopiapp.dataclases.Carrito
import java.text.NumberFormat
import java.util.Locale

class CarritoAdapter(
    private var carritoList: MutableList<Carrito>,
    private val onQuantityChange: (Carrito, Int, Boolean) -> Unit // Added removeItem flag
) : RecyclerView.Adapter<CarritoAdapter.CarritoViewHolder>() {

    class CarritoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tvProductoCarrito)
        val tvPrecio: TextView = itemView.findViewById(R.id.tvPrecioCarrito)
        val etCantidad: TextView = itemView.findViewById(R.id.etCantidadCarrito)
        val btnIncrease: ImageView = itemView.findViewById(R.id.btnIncrease)
        val btnDecrease: ImageView = itemView.findViewById(R.id.btnDecrease)
        val imageView: ImageView = itemView.findViewById(R.id.imageView_product)
    }

    private var suppressTextWatcher = false // Flag to suppress TextWatcher events

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarritoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_carrito, parent, false)
        return CarritoViewHolder(view)
    }

    override fun onBindViewHolder(holder: CarritoViewHolder, position: Int) {
        val carrito = carritoList[position]

        // Set product name and total price
        holder.tvName.text = carrito.nombrePlanta
        val totalPrice = carrito.precioPlanta * carrito.cantidad
        val numberFormat = NumberFormat.getNumberInstance(Locale.GERMANY)
        val formattedPrice = numberFormat.format(totalPrice)
        holder.tvPrecio.text = "$${formattedPrice ?:0}"

        // Aquí puedes cargar la imagen utilizando una biblioteca de carga de imágenes como Glide o Picasso
        Glide.with(holder.itemView.context)
            .load(carrito.url)
            .placeholder(R.drawable.img1)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .apply(RequestOptions().transform(RoundedCorners(16)))
            .error(R.drawable.img)
            .into(holder.imageView)

        // Remove existing TextWatcher
        val existingWatcher = holder.etCantidad.tag as? TextWatcher
        if (existingWatcher != null) {
            holder.etCantidad.removeTextChangedListener(existingWatcher)
        }

        // Set quantity and suppress TextWatcher
        suppressTextWatcher = true
        holder.etCantidad.setText(carrito.cantidad.toString())
        suppressTextWatcher = false

        // Add new TextWatcher
        val newWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                if (suppressTextWatcher) return // Ignore programmatic changes
                val newQuantity = s.toString().toIntOrNull() ?: carrito.cantidad
                if (newQuantity != carrito.cantidad) {
                    onQuantityChange(carrito, newQuantity, newQuantity == 0)
                }
            }
        }
        holder.etCantidad.addTextChangedListener(newWatcher)
        holder.etCantidad.tag = newWatcher

        // Increase button
        holder.btnIncrease.setOnClickListener {
            val newQuantity = carrito.cantidad + 1
            updateQuantity(holder, position, carrito, newQuantity)
        }

        // Decrease button
        holder.btnDecrease.setOnClickListener {
            val newQuantity = carrito.cantidad - 1
            if (newQuantity > 0) {
                updateQuantity(holder, position, carrito, newQuantity)
            } else {
                onQuantityChange(carrito, newQuantity, true) // Remove item if quantity is 0
            }
        }
    }

    override fun getItemCount(): Int = carritoList.size

    private fun updateQuantity(
        holder: CarritoViewHolder,
        position: Int,
        carrito: Carrito,
        newQuantity: Int
    ) {
        // Create a new Carrito object with the updated quantity
        val updatedCarrito = carrito.copy(cantidad = newQuantity)

        // Replace the item in the local list
        carritoList[position] = updatedCarrito

        // Suppress TextWatcher during programmatic updates
        suppressTextWatcher = true
        holder.etCantidad.setText(newQuantity.toString()) // Update EditText
        suppressTextWatcher = false

        // Notify the adapter about the item change
        notifyItemChanged(position)

        // Trigger the callback with the updated item
        onQuantityChange(updatedCarrito, newQuantity, false)
    }

    fun updateList(newList: List<Carrito>) {
        val diffCallback = CarritoDiffCallback(carritoList, newList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        carritoList.clear()
        carritoList.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
    }
}

