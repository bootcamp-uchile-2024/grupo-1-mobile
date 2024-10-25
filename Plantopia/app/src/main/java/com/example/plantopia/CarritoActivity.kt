package com.example.plantopia

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.plantopia.adapter.CarritoAdapter
import com.example.plantopia.data.CarritoItem
import androidx.activity.result.ActivityResult
import com.example.plantopia.R.layout.activity_carrito

class CarritoActivity : AppCompatActivity() {

    // Declaracion variables
    private lateinit var recyclerView: RecyclerView
    private lateinit var carritoAdapter: CarritoAdapter
    private lateinit var layoutManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(activity_carrito)

        recyclerView = findViewById(R.id.recycler_carroCompras)
        layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        carritoAdapter = CarritoAdapter(listOf())
        recyclerView.adapter = carritoAdapter

        val nombre_prod = intent.getStringExtra("nombreProducto").toString()
        val precio_prod = intent.getStringExtra("precioProducto").toString()

        val newCarritoItem = CarritoItem(nombre_prod, precio_prod)

        carritoAdapter.addItem(newCarritoItem)
        carritoAdapter.notifyItemInserted(carritoAdapter.itemCount - 1)

        // Barra Superior Busqueda
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

    }

}