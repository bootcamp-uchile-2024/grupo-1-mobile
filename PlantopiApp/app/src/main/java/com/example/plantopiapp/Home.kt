package com.example.plantopiapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.plantopiapp.catalogo_fragments.FertilizanteFragment
import com.example.plantopiapp.home_fragments.CarritoFragment
import com.example.plantopiapp.home_fragments.HomeFragment
import com.example.plantopiapp.home_fragments.PerfilFragment
import com.example.plantopiapp.catalogo_fragments.PlantasFragment
import com.example.plantopiapp.catalogo_fragments.MaceterosFragment
import com.example.plantopiapp.catalogo_fragments.SustratoFragment
import com.example.plantopiapp.dataclases.LocalUser
import com.example.plantopiapp.room_Carrito.CarritoDatabase
import com.example.plantopiapp.room_LocalUser.LocalUserDao
import com.example.plantopiapp.room_LocalUser.LocalUserDatabase
import com.example.plantopiapp.ui.fragments.MiJardinFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Home : AppCompatActivity() {

    private lateinit var carritoDb: CarritoDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_home)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
            insets
        }

        // Configurar la Toolbar en vez del ActionBar
        val toolbar: Toolbar = findViewById(R.id.toolbar_home)
        setSupportActionBar(toolbar)

        // Configurar el Navigation Drawer
        val drawerLayout: DrawerLayout = findViewById(R.id.activity_home)
        val navView: NavigationView = findViewById(R.id.drawer_layout)
        val navHeader = navView.getHeaderView(0)

        // Configurar el ActionBarDrawerToggle para el Navigation Drawer
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar, R.string.string_open, R.string.string_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        // Recuperar la cadena enviada desde la primera actividad
        val iniSesion = intent.getStringExtra("iniSesion").toString()

        // Manejar la selección de elementos del menú
        navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.Plantas -> replaceFragment(PlantasFragment(), "Catálogo de", "Plantas", iniSesion)
                R.id.Maceteros -> replaceFragment(MaceterosFragment(), "Catálogo de", "Maceteros", iniSesion)
                R.id.Fertilizantes -> replaceFragment(
                    FertilizanteFragment(),
                    "Catálogo de",
                    "Fertilizantes",
                    iniSesion
                )
                R.id.Sustratos -> replaceFragment(SustratoFragment(), "Catálogo de", "Sustratos", iniSesion)
            }
            drawerLayout.closeDrawers()
            true
        }

        // Bottom Navigation View
        val homeFragment = HomeFragment()
        val miJardinFragment = MiJardinFragment()
        val perfilFragment = PerfilFragment()
        val carritoFragment = CarritoFragment()

        replaceFragment(homeFragment, "¡Buen día!", "Plantoper", iniSesion)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> replaceFragment(homeFragment, "¡Buen día!", "Plantoper", iniSesion)
                R.id.mi_jardin -> replaceFragment(miJardinFragment, "Notificaciones", "Mi Jardín", iniSesion)
                R.id.profile -> replaceFragment(perfilFragment, "Preferencias", "Perfil", iniSesion)
                R.id.cart -> replaceFragment(carritoFragment, "Estado del", "Carrito", iniSesion)
            }
            true
        }

        // Initialize Database
        carritoDb = Room.databaseBuilder(
            applicationContext,
            CarritoDatabase::class.java,
            "Carrito.db"
        ).build()

        // Observe total item count for the badge
        observeCartBadge()
    }

    fun updateCartBadge(totalCount: Int) {
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        if (bottomNavigationView == null) {
            Log.e("Home", "BottomNavigationView is null!")
            return
        }

        val badge = bottomNavigationView.getOrCreateBadge(R.id.cart)
        if (totalCount > 0) {
            badge.isVisible = true
            badge.number = totalCount
        } else {
            badge.isVisible = false
        }
    }

    private fun observeCartBadge() {
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        if (bottomNavigationView == null) {
            Log.e("Home", "BottomNavigationView is null!")
            return
        }

        carritoDb.CarritoDao().getTotalItemCount().observe(this) { totalCount ->
            try {
                val badge = bottomNavigationView.getOrCreateBadge(R.id.cart)

                if (badge == null) {
                    Log.e("Home", "Badge is null!")
                    return@observe
                }

                if (totalCount != null && totalCount > 0) {
                    badge.isVisible = true
                    badge.number = totalCount
                } else {
                    badge.isVisible = false
                }
            } catch (e: Exception) {
                Log.e("Home", "Error updating badge: ${e.message}")
            }
        }
    }

    private fun replaceFragment(fragment: Fragment, titulo: String, subtitulo: String, valIniSesion: String) {

        fragment.apply {
            arguments = Bundle().apply {
                putString("iniSesion", valIniSesion)
            }
        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.frame_home, fragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .commit()

        supportActionBar?.title = titulo
        supportActionBar?.subtitle = subtitulo
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.upper_notification_menu, menu)
        return true
    }

}
