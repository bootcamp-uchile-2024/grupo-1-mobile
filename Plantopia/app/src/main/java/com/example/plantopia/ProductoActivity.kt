package com.example.plantopia

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso

class ProductoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_producto)

        // Heredar productos
        val nombre_prod = intent.getStringExtra("texto_producto").toString()
        val nombre_cat = intent.getStringExtra("texto_categoria").toString()
        val precio = intent.getStringExtra("texto_precio").toString()
        val descripcion = intent.getStringExtra("texto_descripcion").toString()
        val image_url = intent.getStringExtra("url").toString()
        val valoracion = intent.getStringExtra("valoracion").toString()
        val luz_req = intent.getStringExtra("luz_req").toString()
        val frec_riego = intent.getStringExtra("frec_riego").toString()
        val humedad_rec = intent.getStringExtra("humedad_rec").toString()
        val habitat = intent.getStringExtra("habitat").toString()
        val suelo = intent.getStringExtra("suelo").toString()
        val cuidado = intent.getStringExtra("cuidado").toString()
        val estacion = intent.getStringExtra("estacion").toString()

        // Display producto

        // Nombre, categoria y precio
        val producto: TextView = findViewById(R.id.nombre_producto)
        producto.text = nombre_prod
        val precio_prod: TextView = findViewById(R.id.precio_producto)
        precio_prod.text = "$" + precio
        val cat: TextView = findViewById(R.id.category)
        cat.text = "Categoría: " + nombre_cat + " - Valoración: " + valoracion + "/10"

        // Imagen
        val fotoProducto: ImageView = findViewById(R.id.fotoProducto)
        val strFotoProducto = image_url
        Picasso.get()
            .load(strFotoProducto)
            .resize(500,500)
            .centerCrop()
            .into(fotoProducto)

        // Descripciones
        val descr: TextView = findViewById(R.id.description)
        descr.text = descripcion

        // if planta
        val contenedorAtributos: LinearLayout = findViewById(R.id.contenedor_atributos)

        val luz: TextView = TextView(this)
        luz.text = "Luz Requerida: " + luz_req
        val riego: TextView = TextView(this)
        riego.text = "Frecuencia de Riego: " + frec_riego
        val hum: TextView = TextView(this)
        hum.text = "Humedad Requerida: " + humedad_rec
        val hab: TextView = TextView(this)
        hab.text = "Hábitat: " + habitat
        val soil: TextView = TextView(this)
        soil.text = "Suelo: " + suelo
        val care: TextView = TextView(this)
        care.text = "Dificultad de Cuidado: " + cuidado
        val season: TextView = TextView(this)
        season.text = "Estación: " + estacion

        contenedorAtributos.addView(luz)
        contenedorAtributos.addView(riego)
        contenedorAtributos.addView(hum)
        contenedorAtributos.addView(hab)
        contenedorAtributos.addView(soil)
        contenedorAtributos.addView(care)
        contenedorAtributos.addView(season)

        val botonAlCarro: Button = findViewById(R.id.button2)
        botonAlCarro.setOnClickListener(View.OnClickListener {
            val add_carro = Intent(this, CarritoActivity::class.java)
            startActivity(add_carro)
        })

        val buscar: ImageView = findViewById(R.id.lupa)
        buscar.setOnClickListener(View.OnClickListener {
            val go_catalogo = Intent(this, CatalogoActivity::class.java)
            startActivity(go_catalogo)
        })

        // Menu Inferior
        // Catalogo
        val iconoMenu: ImageView = findViewById(R.id.menu)
        iconoMenu.setOnClickListener(View.OnClickListener {
            val go_menu = Intent(this, CatalogoActivity::class.java)
            startActivity(go_menu)
        })
        // Home
        val iconoHome: ImageView = findViewById(R.id.home)
        iconoHome.setOnClickListener(View.OnClickListener {
            val go_home = Intent(this, MainActivity::class.java)
            startActivity(go_home)
        })
        // User
        val iconoUser: ImageView = findViewById(R.id.user)
        iconoUser.setOnClickListener(View.OnClickListener {
            val go_user = Intent(this, UserActivity::class.java)
             startActivity(go_user)
        })
        // Carro
        val iconoCarro: ImageView = findViewById(R.id.carro)
        iconoCarro.setOnClickListener(View.OnClickListener {
            val go_carro = Intent(this, CarritoActivity::class.java)
            startActivity(go_carro)
        })

    }
}