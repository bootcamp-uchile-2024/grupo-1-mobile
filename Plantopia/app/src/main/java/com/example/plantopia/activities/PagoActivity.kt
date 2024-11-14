package com.example.plantopia.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.plantopia.R
import com.example.plantopia.data.CarritoItem
import com.example.plantopia.openhelper.CarritoOpenHelper

class PagoActivity : AppCompatActivity() {

    private lateinit var itemList: List<CarritoItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pago)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Leo la base de datos para calcular el total
        val dbHelper= CarritoOpenHelper(this)
        val db = dbHelper.readableDatabase

        val cursor = db.query("carrito", null, null, null, null, null, null)
        itemList = mutableListOf()
        var sub_total: Int = 0
        with(cursor) {
            while (moveToNext()) {
                val name = getString(1)
                val price = getString(2)
                itemList += CarritoItem(name, price)
                sub_total += price.toInt()
            }
        }
        cursor.close()
        db.close()

        println(sub_total)

        var envio: TextView = findViewById(R.id.textView_envio)
        envio.text = "5000"

        var subtotal: TextView = findViewById(R.id.textView_valorTotal)
        subtotal.text = sub_total.toString()

        var total: TextView = findViewById(R.id.textView_totalsuma)
        var suma_compra_envio = envio.text.toString().toInt() + subtotal.text.toString().toInt()
        total.text = suma_compra_envio.toString()
    }

    fun onMethodButtonClick(view: View) {
        val go_metodo = Intent(this, CompraFinalizadaActivity::class.java)
        startActivity(go_metodo)
    }

    fun onBackButtonClick(view: View) {
        val intent = Intent(this, DondeActivity::class.java)
        startActivity(intent)
    }
}