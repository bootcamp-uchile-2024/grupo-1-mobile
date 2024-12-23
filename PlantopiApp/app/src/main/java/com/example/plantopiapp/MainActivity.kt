package com.example.plantopiapp

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.plantopiapp.clientes.UsuarioGetRetrofitClient
import com.example.plantopiapp.dataclases.LocalUser
import com.example.plantopiapp.dataclases.UsuarioGet
import com.example.plantopiapp.room_LocalUser.LocalUserDatabase
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Obtener los campos para el inicio de sesion
        val iniSesion = findViewById<AppCompatButton>(R.id.button_inisesion)
        val ev_email = findViewById<TextView>(R.id.editText_correo)
        val ev_contrasena = findViewById<TextView>(R.id.editText_contrasena)

        iniSesion.setOnClickListener {
            lifecycleScope.launch {
                val usuario = getUsuario(ev_email.text.toString(), ev_contrasena.text.toString())

                if (usuario != null) {

                    val localUserDb = Room.databaseBuilder(
                        applicationContext,
                        LocalUserDatabase::class.java,
                        "LocalUser.db"
                    ).build()

                    val localUserDao = localUserDb.localUserDao()

                    // Save the user information in the local database
                    val localUser = LocalUser(
                        rutUsuario = usuario.rutUsuario,
                        nombres = usuario.nombres,
                        apellidos = usuario.apellidos,
                        email = usuario.email,
                        clave = usuario.clave,
                        telefono = usuario.telefono,
                        direccion = usuario.direccion,
                        idComuna = usuario.idComuna,
                        codigoPostal = usuario.codigoPostal,
                        idPerfil = usuario.idPerfil,
                        respuesta1 = usuario.Preferencias[0].respuesta1,
                        respuesta2 = usuario.Preferencias[0].respuesta2,
                        respuesta3 = usuario.Preferencias[0].respuesta3,
                        respuesta4 = usuario.Preferencias[0].respuesta4,
                        respuesta5 = usuario.Preferencias[0].respuesta5,
                        respuesta6 = usuario.Preferencias[0].respuesta6,
                        respuesta7 = usuario.Preferencias[0].respuesta7,
                        respuesta8 = usuario.Preferencias[0].respuesta8,
                        respuesta9 = usuario.Preferencias[0].respuesta9,
                        id = 1)

                    localUserDao.insertUser(localUser)
                    Toast.makeText(this@MainActivity, "Inicio exitoso", Toast.LENGTH_SHORT).show()

                    // Send the information to the other activity
                    val intent = Intent(this@MainActivity, Home::class.java)
                    intent.putExtra("iniSesion", "true")
                    startActivity(intent)

                } else {
                    Toast.makeText(this@MainActivity, "Credenciales incorrectas", Toast.LENGTH_SHORT).show()
                }
            }

        }

    }

    fun onRegisterButtonClick(view: View) {
        val intent = Intent(this, Register::class.java)
        startActivity(intent)
    }

    fun onInvitadoButtonClick(view: View) {
        val intent = Intent(this, Home::class.java)
        startActivity(intent)
    }

    fun onRecuperaPassButtonClick(view: View) {
        val intent = Intent(this, RecuperaPass::class.java)
        startActivity(intent)
    }

    suspend fun getUsuario(email: String, password: String): UsuarioGet? {
        val response = UsuarioGetRetrofitClient.instance.getUsuarioByEmailtAndPass(email, password)

        return if (response.isSuccessful) {
            response.body()?.firstOrNull{ it.email == email && it.clave == password } // Returns the first user or null if the list is empty
        } else {
            runOnUiThread {
                val ev_errorIniSesion: TextView = findViewById(R.id.tv_errorIniSesion)
                ev_errorIniSesion.visibility = View.VISIBLE
            }
            null
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "Recordatorios_riego",
                "Recordatorios riego",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Recordatorio para regar las plantas"
            }
            val manager = getSystemService(NotificationManager::class.java)
            manager?.createNotificationChannel(channel)
        }
    }



}