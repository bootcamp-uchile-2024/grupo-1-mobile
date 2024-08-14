package com.example.plantopia

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.utils.widget.MockView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

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

        val iconoPlanta: ImageView = findViewById(R.id.imageView5)
        iconoPlanta.setOnClickListener(View.OnClickListener {
            val go_product = Intent(this, ProductoActivity::class.java)
            startActivity(go_product)
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