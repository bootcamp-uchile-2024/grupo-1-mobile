package com.example.plantopia.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.plantopia.R
import com.example.plantopia.data.CarritoItem

class CarritoAdapter(var itemList: List<CarritoItem>):
    // Clase adapter para recycler view

    RecyclerView.Adapter<CarritoAdapter.MyViewHolder>() {

        // Clase interna para el ViewHolder
        class MyViewHolder(itemView: View) :
            RecyclerView.ViewHolder(itemView){
            val productoCarrito: TextView = itemView.findViewById(R.id.textView_NombreProductoCarrito)
            val precioCarrito: TextView = itemView.findViewById(R.id.textView_PrecioCarrito)
        }

        // Crea el view holder
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_carrito, parent, false)
            return MyViewHolder(itemView)
        }

        // Enlaza los datos con el view holder
        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
                holder.productoCarrito.text = itemList[position].productoCarrito.toString()
                holder.precioCarrito.text = itemList[position].precioCarrito.toString()
        }

        // Devuelve el número de elementos en la lista
        override fun getItemCount() = itemList.size

        // Agrega un nuevo elemento a la lista
        fun addItem(item: CarritoItem) {
            itemList += item
            notifyItemInserted(getItemCount() - 1)
        }

        // Obtiene un elemento de la lista
        fun getItem(position: Int): CarritoItem {
        return itemList[position]
        }

        // Borra todos los elementos de la lista
        fun clear() {
            itemList = listOf()
            notifyDataSetChanged()
        }
}