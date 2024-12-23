package com.example.plantopiapp.register_fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.plantopiapp.Constants
import com.example.plantopiapp.Home
import com.example.plantopiapp.R
import com.example.plantopiapp.clases.ProgressBarController
import com.example.plantopiapp.dataclases.Preferencias
import com.example.plantopiapp.dataclases.Usuario
import com.example.plantopiapp.servicios.UsuarioService
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Register_4th : Fragment() {

    private var progressBarController: ProgressBarController? = null
    private var progress = 100

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ProgressBarController) {
            progressBarController = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        progressBarController = null
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_register_4th, container, false)

        // Retrieve arguments
        val in_mail = arguments?.getString("mail")
        val in_name = arguments?.getString("name")
        val in_lastname = arguments?.getString("lastname")
        val in_password = arguments?.getString("password")
        val in_run = arguments?.getString("run")
        val in_telefono = arguments?.getString("telefono")
        val in_direccion = arguments?.getString("direccion")
        val in_comuna = arguments?.getString("comuna")
        val in_codigo_postal = arguments?.getString("codigo_postal")

        // ChipGroup references
        val cuidadoChipGroup = view.findViewById<ChipGroup>(R.id.chipGroupCuidadoSemanal)
        val espacioChipGroup = view.findViewById<ChipGroup>(R.id.chipGroupEspacio)
        val iluminacionChipGroup = view.findViewById<ChipGroup>(R.id.chipGroupIluminacion)
        val temperaturaChipGroup = view.findViewById<ChipGroup>(R.id.chipGroupTemperatura)
        val petFriendlyChipGroup = view.findViewById<ChipGroup>(R.id.chipGroupMascotas)

        // Change the style of the selected chips
        changeStyleOfSelected(cuidadoChipGroup)
        changeStyleOfSelected(espacioChipGroup)
        changeStyleOfSelected(iluminacionChipGroup)
        changeStyleOfSelected(temperaturaChipGroup)
        changeStyleOfSelected(petFriendlyChipGroup)

        // Button action
        val button = view.findViewById<View>(R.id.completar_encuesta)
        button.setOnClickListener {
            val selectedCuidado = getSelectedChipText(cuidadoChipGroup).toString()
            Log.d("MEV", "Cuidado seleccionado: $selectedCuidado")
            val selectedEspacio = getSelectedChipText(espacioChipGroup).toString()
            Log.d("MEV", "Espacio seleccionado: $selectedEspacio")
            val selectedIluminacion = getSelectedChipText(iluminacionChipGroup).toString()
            Log.d("MEV", "Iluminacion seleccionada: $selectedIluminacion")
            val selectedTemperatura = getSelectedChipText(temperaturaChipGroup).toString()
            Log.d("MEV", "Temperatura seleccionada: $selectedTemperatura")
            val selectedPetFriendly = getSelectedChipText(petFriendlyChipGroup).toString()
            Log.d("MEV", "Mascotas seleccionadas: $selectedPetFriendly")

            if (selectedCuidado.isEmpty() || selectedEspacio.isEmpty() || selectedIluminacion.isEmpty() ||
                selectedTemperatura.isEmpty() || selectedPetFriendly.isEmpty()) {
                Toast.makeText(context, "Seleccione un valor para cada pregunta", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            lifecycleScope.launch {
                val nuevo_usuario = Usuario(
                    rutUsuario = in_run ?: "",
                    nombres = in_name ?: "",
                    apellidos = in_lastname ?: "",
                    email = in_mail.toString(),
                    clave = in_password.toString(),
                    telefono = in_telefono?.toInt() ?: 0,
                    direccion = in_direccion ?: "",
                    idComuna = in_comuna?.toInt() ?: 0,
                    codigoPostal = in_codigo_postal ?: "",
                    idPerfil = 2,
                    respuesta1 = selectedCuidado,
                    respuesta2 = selectedEspacio,
                    respuesta3 = selectedIluminacion,
                    respuesta4 = selectedTemperatura,
                    respuesta5 = selectedPetFriendly,
                    respuesta6 = "",
                    respuesta7 = "",
                    respuesta8 = "",
                    respuesta9 = ""
                    )

                val usuarioCreado = registrarUsuario(nuevo_usuario)
                if (usuarioCreado != null) {
                    Toast.makeText(
                        context,
                        "Usuario creado: ${usuarioCreado.nombres}",
                        Toast.LENGTH_SHORT
                    ).show()
                    goToUser()
                } else {
                    Toast.makeText(context, "Error al crear usuario", Toast.LENGTH_SHORT).show()
                }
            }
        }

        simulateProgress()

        return view
    }

    private fun getSelectedChipText(chipGroup: ChipGroup): String {
        val selectedChipId = chipGroup.checkedChipId
        return view?.findViewById<Chip>(selectedChipId)?.text?.toString() ?: ""
    }

    private fun goToUser() {
        val intent = Intent(context, Home::class.java)
        startActivity(intent)
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    suspend fun registrarUsuario(usuario: Usuario): Usuario? {
        val response = getRetrofit().create(UsuarioService::class.java).addUsuario(usuario)
        return if (response.isSuccessful) {
            response.body()
        } else {
            null
        }
    }

    private fun simulateProgress() {
        progressBarController?.updateProgress(progress)
    }

    private fun changeStyleOfSelected(chipGroup: ChipGroup) {
        chipGroup.setOnCheckedStateChangeListener { group, checkedIds ->
            for (i in 0 until group.childCount) {
                val chip = group.getChildAt(i) as? Chip ?: continue

                if (checkedIds.contains(chip.id)) {
                    // Apply selected style
                    chip.setChipBackgroundColorResource(R.color.Buttons_Primary_Background_primary_button_light)
                    chip.setTextColor(ContextCompat.getColor(requireContext(), R.color.Buttons_Secundary_Text_secundary_texthover_dark)) // Adjust for selected text color
                    chip.chipStrokeWidth = 2f // Increase stroke width
                    chip.setChipStrokeColorResource(R.color.Buttons_Secundary_Background_secundary_hover_butt_light)
                } else {
                    // Apply default style
                    chip.setChipBackgroundColorResource(R.color.Buttons_Primary_Text_text_primary_light)
                    chip.setTextColor(ContextCompat.getColor(requireContext(), R.color.Buttons_Primary_Background_primary_button_light))
                    chip.chipStrokeWidth = 0f // Remove stroke
                }
            }
        }
    }
}
