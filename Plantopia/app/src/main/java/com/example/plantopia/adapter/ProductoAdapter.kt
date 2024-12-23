package com.example.plantopia.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.plantopia.R
import com.example.plantopia.activities.ProductoActivity
import com.example.plantopia.data.ProductoItem
import com.squareup.picasso.Picasso

class ProductoAdapter (var itemList: List<ProductoItem>):
    RecyclerView.Adapter<ProductoAdapter.MyViewHolder>() {

        class MyViewHolder(itemView: View) :
            RecyclerView.ViewHolder(itemView){
            val producto: TextView = itemView.findViewById(R.id.textView_Producto)
            val precio: TextView = itemView.findViewById(R.id.textView_PrecioProducto)
            val image: ImageView = itemView.findViewById(R.id.imageView_Producto)

            init {
                itemView.setOnClickListener {
                    val position: Int = adapterPosition
                    Toast.makeText(itemView.context, "Has seleccionado el producto ${position}", Toast.LENGTH_LONG).show()
                }
            }


        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_producto, parent, false)
            return MyViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            holder.producto.text = itemList[position].nombreProducto.toString()
            holder.precio.text = itemList[position].precioNormal.toString()

            /*
            val location = holder.image

            val strFotoProducto =itemList[position].url
            Picasso.get()
                .load(strFotoProducto)
                .resize(400,400)
                .centerCrop()
                .into(location)

             */

            holder.itemView.setOnClickListener {
                val intent = Intent(holder.itemView.context, ProductoActivity::class.java)
                intent.putExtra("texto_producto", itemList[position].nombreProducto)
                intent.putExtra("descuento", itemList[position].descuento)
                intent.putExtra("texto_descripcion", itemList[position].descripcion)
                intent.putExtra("url", itemList[position].url)
                intent.putExtra("valoracion", itemList[position].valoracion.toString())

                /*
                intent.putExtra("luz_req", itemList[position].luzRequerida)
                intent.putExtra("frec_riego", itemList[position].frecuenciaDeRiego)
                intent.putExtra("humedad_rec", itemList[position].nivelDeHumedad)
                intent.putExtra("habitat", itemList[position].habitat)
                intent.putExtra("suelo", itemList[position].tipoDeSuelo)
                intent.putExtra("cuidado", itemList[position].dificultadDeCuidado)
                intent.putExtra("estacion", itemList[position].estacion)
                */

                holder.itemView.context.startActivity(intent)
            }

        }

        override fun getItemCount() = itemList.size

        }
