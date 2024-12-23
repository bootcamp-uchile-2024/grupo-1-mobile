package com.example.plantopia.activities

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.plantopia.R
import com.example.plantopia.data.CarritoItem
import com.example.plantopia.helper.CarritoOpenHelper
import com.example.plantopia.helper.ComprasOpenHelper

class DondeActivity : AppCompatActivity() {

    private lateinit var itemList: List<CarritoItem>
    // private lateinit var itemList_c: List<CompraItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_donde)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

    }

    fun onSiguienteButtonClick(view: View) {

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


        // Base de datos Compra

        val firstName: EditText = findViewById(R.id.editText_Name)
        val lastName: EditText = findViewById(R.id.editText_apellido)
        val rut: EditText = findViewById(R.id.editText_rut)
        val direccion: EditText = findViewById(R.id.editText_direccion)
        val mail: EditText = findViewById(R.id.editText_mail)
        val telefono: EditText = findViewById(R.id.editText_telefono)
        val comuna: EditText = findViewById(R.id.editText_comuna)
        val region: EditText = findViewById(R.id.editText_region)

        val dpHelper_c = ComprasOpenHelper(this)
        val db_c = dpHelper_c.writableDatabase

        val values = ContentValues().apply {
            put("email_compras", mail.text.toString())
            put("nombre_compras", firstName.text.toString())
            put("apellido_compras", lastName.text.toString())
            put("rut_compras", rut.text.toString())
            put("direccion_compras", direccion.text.toString())
            put("comuna_compras", comuna.text.toString())
            put("region_compras", region.text.toString())
            put("telefono_compras", telefono.text.toString())
            put("total_compras", sub_total.toString())
        }
        db_c.insert("compras", null, values)
        db_c.close()

        val go_pay = Intent(this, PagoActivity::class.java)
        startActivity(go_pay)

    }

    fun onAtrasButtonClick(view: View) {
        //if (CheckAllFields()) {
        val intent = Intent(this, CarritoActivity::class.java)
        startActivity(intent)
        //}
    }

    /*fun CheckAllFields(): Boolean {
        if (mail!!.length() == 0) {
            mail!!.error = "This field is required"
            return false
        }
        if (firstName!!.length() == 0) {
            firstName!!.error = "This field is required"
            return false
        }
        if (lastName!!.length() == 0) {
            lastName!!.error = "This field is required"
            return false
        }
        if (rut!!.length() == 0) {
            rut!!.error = "This field is required"
            return false
        }
        if (direccion!!.length() == 0) {
            direccion!!.error = "This field is required"
            return false
        }
        if (comuna!!.length() == 0) {
            comuna!!.error = "This field is required"
            return false
        }
        if (region!!.length() == 0) {
            region!!.error = "This field is required"
            return false
        }
        if (telefono!!.length() == 0) {
            telefono!!.error = "This field is required"
            return false
        } else if (telefono!!.length() != 13) {
            telefono!!.error = "El teléfono debe incluir 13 digitos"
            return false
        }
        // after all validation return true.
        return true
    }*/
}