package com.example.plantopia.activities

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.plantopia.R
import com.google.gson.GsonBuilder
import com.squareup.picasso.Picasso

class CatalogoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_catalogo)

        val buscar: ImageView = findViewById(R.id.imageView_buscar)
        buscar.setOnClickListener(View.OnClickListener {
            val go_catalogo = Intent(this, CatalogoActivity::class.java)
            startActivity(go_catalogo)
        })

        // Contenedor a agregar productos
        val contenedorLista: LinearLayout = findViewById(R.id.contenedor_listaProductos)


        // Lectura JSON
        val filePath = resources.openRawResource(R.raw.producto)
        val jsonString = filePath.bufferedReader().use{it.readText()}

        // Clase
        data class Producto(val nombreProducto: String, val stock: Int, val precio: Int,
                            val descripcion: String, val url: String, val valoracion: Float,
                            val categoria: String, val LuzRequerida: String,
                            val FrecuenciaDeRiego: String, val NivelDeHumedad: String,
                            val Habitat: String, val TipoDeSuelo: String,
                            val DificultadDeCuidado: String, val Estacion: String)

        data class Productos(val id: String, val atributos: Producto)

        val gson = GsonBuilder().create()
        val lista_productos = gson.fromJson(jsonString, Array<Productos>::class.java).toList()

        for (i in 0 until lista_productos.size){

            val contenedorH: LinearLayout = LinearLayout(this)
            contenedorH.orientation = LinearLayout.HORIZONTAL
            val contenedorV: LinearLayout = LinearLayout(this)
            contenedorV.orientation = LinearLayout.VERTICAL

            contenedorH.apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, // Horizontal
                    ViewGroup.LayoutParams.WRAP_CONTENT // Vertical
                )
            }
            contenedorH.gravity = Gravity.LEFT

            contenedorV.apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, // Horizontal
                    ViewGroup.LayoutParams.WRAP_CONTENT // Vertical
                )
            }
            contenedorV.gravity = Gravity.LEFT

            val fotoProducto = ImageView(this)

            val strFotoProducto = lista_productos[i].atributos.url
            Picasso.get()
                .load(strFotoProducto)
                .resize(400,400)
                .centerCrop()
                .into(fotoProducto)

            val texto_producto: String = lista_productos[i].atributos.nombreProducto
            val producto: TextView = TextView(this)
            producto.text = texto_producto

            val texto_categoria: String = lista_productos[i].atributos.categoria
            val categoria: TextView = TextView(this)
            categoria.text = texto_categoria

            val texto_precio: String = lista_productos[i].atributos.precio.toString()
            val precio: TextView = TextView(this)
            precio.text = "$" + texto_precio

            val viewButton: Button = Button(this)
            viewButton.text = "Ver detalle"

            viewButton.setOnClickListener(View.OnClickListener {
                val go_product = Intent(this, ProductoActivity::class.java)
                go_product.putExtra("texto_producto", lista_productos[i].atributos.nombreProducto)
                go_product.putExtra("texto_categoria", lista_productos[i].atributos.categoria)
                go_product.putExtra("texto_precio", lista_productos[i].atributos.precio.toString())
                go_product.putExtra("texto_descripcion", lista_productos[i].atributos.descripcion)
                go_product.putExtra("url", lista_productos[i].atributos.url)
                go_product.putExtra("valoracion", lista_productos[i].atributos.valoracion.toString())
                go_product.putExtra("luz_req", lista_productos[i].atributos.LuzRequerida)
                go_product.putExtra("frec_riego", lista_productos[i].atributos.FrecuenciaDeRiego)
                go_product.putExtra("humedad_rec", lista_productos[i].atributos.NivelDeHumedad)
                go_product.putExtra("habitat", lista_productos[i].atributos.Habitat)
                go_product.putExtra("suelo", lista_productos[i].atributos.TipoDeSuelo)
                go_product.putExtra("cuidado", lista_productos[i].atributos.DificultadDeCuidado)
                go_product.putExtra("estacion", lista_productos[i].atributos.Estacion)
                startActivity(go_product)
            })



            contenedorH.addView(fotoProducto)
            contenedorH.addView(contenedorV)
            contenedorV.addView(producto)
            contenedorV.addView(categoria)
            contenedorV.addView(precio)
            contenedorV.addView(viewButton)

            contenedorH.setPadding(40,40,40,40)
            contenedorLista.addView(contenedorH)
        }

        // Menu Inferior
        // Catalogo
        val iconoMenu: ImageView = findViewById(R.id.imageView_menu)
        iconoMenu.setOnClickListener(View.OnClickListener {
            val go_menu = Intent(this, CatalogoActivity::class.java)
            startActivity(go_menu)
        })
        // Home
        val iconoHome: ImageView = findViewById(R.id.imageView_home)
        iconoHome.setOnClickListener(View.OnClickListener {
            val go_home = Intent(this, MainActivity::class.java)
            startActivity(go_home)
        })
        // User
        val iconoUser: ImageView = findViewById(R.id.imageView_user)
        iconoUser.setOnClickListener(View.OnClickListener {
            val go_user = Intent(this, UserActivity::class.java)
            startActivity(go_user)
        })
        // Carro
        val iconoCarro: ImageView = findViewById(R.id.imageView_carro)
        iconoCarro.setOnClickListener(View.OnClickListener {
            val go_carro = Intent(this, CarritoActivity::class.java)
            startActivity(go_carro)
        })

    }
}