package com.example.plantopia.activities

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.plantopia.R
import com.example.plantopia.R.layout.activity_producto
import com.example.plantopia.helper.CarritoOpenHelper
import com.squareup.picasso.Picasso

class ProductoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(activity_producto)

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
        val precio_prod: TextView = findViewById(R.id.textView_precio)
        precio_prod.text = precio
        val cat: TextView = findViewById(R.id.textView_category)
        cat.text = "Categoría: " + nombre_cat + " - Valoración: " + valoracion + "/10"

        // Imagen
        val fotoProducto: ImageView = findViewById(R.id.imageView_producto)
        val strFotoProducto = image_url
        Picasso.get()
            .load(strFotoProducto)
            .resize(500,500)
            .centerCrop()
            .into(fotoProducto)

        // Descripciones
        val descr: TextView = findViewById(R.id.textView_description)
        descr.text = descripcion

        // if planta
        val contenedorAtributos: LinearLayout = findViewById(R.id.contenedor_atributos)

        val luz = TextView(this)
        luz.text = "Luz Requerida: " + luz_req
        val riego= TextView(this)
        riego.text = "Frecuencia de Riego: " + frec_riego
        val hum= TextView(this)
        hum.text = "Humedad Requerida: " + humedad_rec
        val hab= TextView(this)
        hab.text = "Hábitat: " + habitat
        val soil= TextView(this)
        soil.text = "Suelo: " + suelo
        val care = TextView(this)
        care.text = "Dificultad de Cuidado: " + cuidado
        val season = TextView(this)
        season.text = "Estación: " + estacion

        contenedorAtributos.addView(luz)
        contenedorAtributos.addView(riego)
        contenedorAtributos.addView(hum)
        contenedorAtributos.addView(hab)
        contenedorAtributos.addView(soil)
        contenedorAtributos.addView(care)
        contenedorAtributos.addView(season)

        val botonAlCarro: Button = findViewById(R.id.button_addCarrito)
        botonAlCarro.setOnClickListener(View.OnClickListener {
            val add_carro = Intent(this, CarritoActivity::class.java)
            startActivity(add_carro)
        })

        val buscar: ImageView = findViewById(R.id.imageView_buscar)
        buscar.setOnClickListener(View.OnClickListener {
            val go_catalogo = Intent(this, CatalogoActivity::class.java)
            startActivity(go_catalogo)
        })

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

        // Agregar producto
        val add_carro = findViewById<Button>(R.id.button_addCarrito)
        add_carro.setOnClickListener {
            onAddButtonClick(it)
        }

    }

    fun onAddButtonClick(view: View) {
        // Obtener los valores de los productos a agregar al carrito
        val nombreProducto = findViewById<TextView>(R.id.nombre_producto).text.toString()
        val precioProducto = findViewById<TextView>(R.id.textView_precio).text.toString()

        /*
        val go_product = Intent(this, CarritoActivity::class.java)
        go_product.putExtra("nombreProducto", nombreProducto)
        go_product.putExtra("precioProducto", precioProducto)
         */

        val dpHelper = CarritoOpenHelper(this)
        val db = dpHelper.writableDatabase

        val values = ContentValues().apply {
            put("producto", nombreProducto)
            put("precio", precioProducto)
        }
        db.insert("carrito", null, values)
        db.close()

        finish()
    }

}