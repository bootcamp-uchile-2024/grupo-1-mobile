package com.example.plantopia

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Im
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.utils.widget.MockView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.gson.GsonBuilder
import com.squareup.picasso.Picasso
import java.io.File
import java.io.InputStream
import java.net.URL

class CatalogoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_catalogo)

        val buscar: ImageView = findViewById(R.id.lupa)
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
        data class Producto(val id_producto: String, val nombre_producto: String, val descripcion: String,
                            val enlace_foto: String, var inventario: Int)

        data class Productos(val id: String, val atributos: Producto)

        val gson = GsonBuilder().create()
        val lista_productos = gson.fromJson(jsonString, Array<Productos>::class.java).toList()

        // Testeo productos lectura
        // val largo_lista = lista_productos.size
        // println("Hay $largo_lista productos disponibles")
        // var primer_prod = lista_productos[0].atributos.nombre_producto
        // println("El primer producto es: $primer_prod")
        // println(lista_productos[0])
        // val texto_titulo: TextView = findViewById(R.id.producto)
        // texto_titulo.text = primer_prod

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

            val strFotoProducto = lista_productos[i].atributos.enlace_foto
            Picasso.get()
                .load(strFotoProducto)
                .resize(400,400)
                .centerCrop()
                .into(fotoProducto)

            val texto_producto: String = lista_productos[i].atributos.nombre_producto
            val producto: TextView = TextView(this)
            producto.text = texto_producto

            val viewButton: Button = Button(this)
            viewButton.text = "Ver detalle"

            viewButton.setOnClickListener(View.OnClickListener {
                val go_product = Intent(this, ProductoActivity::class.java)
                startActivity(go_product)
            })

            contenedorH.addView(fotoProducto)
            contenedorH.addView(contenedorV)
            contenedorV.addView(producto)
            contenedorV.addView(viewButton)

            contenedorH.setPadding(40,40,40,40)
            contenedorLista.addView(contenedorH)
        }

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