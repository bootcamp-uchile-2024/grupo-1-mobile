package com.example.plantopia.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.plantopia.adapter.CarritoAdapter
import com.example.plantopia.data.CarritoItem
import com.example.plantopia.R
import com.example.plantopia.R.layout.activity_carrito
import com.example.plantopia.openhelper.CarritoOpenHelper

class CarritoActivity : AppCompatActivity() {

    // Declaracion variables
    private lateinit var recyclerView: RecyclerView
    private lateinit var carritoAdapter: CarritoAdapter
    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var itemList: List<CarritoItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(activity_carrito)

        recyclerView = findViewById(R.id.recycler_carroCompras)
        layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        carritoAdapter = CarritoAdapter(listOf())
        recyclerView.adapter = carritoAdapter
        registerForContextMenu(recyclerView)

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
    @SuppressLint("NotifyDataSetChanged")
    override fun onResume() {
        super.onResume()

        val dbHelper= CarritoOpenHelper(this)
        val db = dbHelper.readableDatabase

        var total_view: TextView = findViewById(R.id.textView_suma)
        var total = 0.0


        val cursor = db.query("carrito", null, null, null, null, null, null)
        itemList = mutableListOf()
        with(cursor) {
            while (moveToNext()) {
                val name = getString(1)
                val price = getString(2)
                itemList += CarritoItem(name, price)
                total += price.toDouble()
            }
        }
        cursor.close()
        db.close()

        // Actualizar el textview con el total
        total_view.text = total.toString()

        carritoAdapter.clear()
        carritoAdapter.itemList = itemList
        carritoAdapter.notifyDataSetChanged()
    }

    fun onPayButtonClick(view: View) {
        val total = findViewById<TextView>(R.id.textView_suma).text.toString()
        val intent = Intent(this, DondeActivity::class.java)
        intent.putExtra("sumaPlantas", total)
        startActivity(intent)
    }

}