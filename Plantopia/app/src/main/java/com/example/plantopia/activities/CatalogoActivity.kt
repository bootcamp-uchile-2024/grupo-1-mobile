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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.plantopia.R
import com.example.plantopia.adapter.ProductoAdapter
import com.example.plantopia.data.ProductoItem
import com.example.plantopia.helper.webServiceHelper
import com.google.gson.GsonBuilder
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

const val BASE_URL = "16.171.43.137:4000"

class CatalogoActivity : AppCompatActivity() {

    private lateinit var webServiceHelper: webServiceHelper

    // Declaracion variables
    private lateinit var recyclerView: RecyclerView
    private lateinit var ProductoAdapter: ProductoAdapter
    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var itemList: List<ProductoItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_catalogo)
        val buscar: ImageView = findViewById(R.id.imageView_buscar)
        buscar.setOnClickListener(View.OnClickListener {
            val go_catalogo = Intent(this, CatalogoActivity::class.java)
            startActivity(go_catalogo)
        })

        recyclerView = findViewById(R.id.reciclerView_productos)
        layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)

        webServiceHelper = webServiceHelper(BASE_URL)

        // Descargar la lista d productos

        CoroutineScope(Dispatchers.IO).launch {
            val listaProductos = webServiceHelper.getGETRequest("/productos/catalogo?page=1&size=35")
            val gson = GsonBuilder().create()
            val lista_productos = gson.fromJson(listaProductos, Array<ProductoItem>::class.java).toList()

            println(listaProductos)


        }

        //recyclerView.adapter = ProductoAdapter(lista_productos)






        // Lectura JSON
        // val filePath = resources.openRawResource(R.raw.catalogo)
        // val jsonString = filePath.bufferedReader().use{it.readText()}
        // val gson = GsonBuilder().create()
        // val lista_productos = gson.fromJson(jsonString, Array<ProductoItem>::class.java).toList()




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