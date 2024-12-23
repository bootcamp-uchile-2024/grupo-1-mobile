package com.example.plantopiapp.register_fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.Checkable
import android.widget.EditText
import com.example.plantopiapp.clases.ProgressBarController
import com.example.plantopiapp.R
import com.example.plantopiapp.catalogo_fragments.ProductoFragment

class Register_1st : Fragment(R.layout.fragment_register_1st) {

    private var progressBarController: ProgressBarController? = null
    private var progress = 20

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        simulateProgress()

        val in_mail = view.findViewById<EditText>(R.id.editText_correo_registro)
        val in_name = view.findViewById<EditText>(R.id.editText_nombre_registro)
        val in_lastname = view.findViewById<EditText>(R.id.editText_apellido_registro)
        val in_password = view.findViewById<EditText>(R.id.editText_contrasena_registro)
        val in_password2 = view.findViewById<EditText>(R.id.editText_contrasena2_registro)
        val in_recomendaciones = view.findViewById<CheckBox>(R.id.checkBox_recomendaciones)
        val in_terminos = view.findViewById<CheckBox>(R.id.checkBox_terminos)

        val button = view.findViewById<Button>(R.id.button_register1st)
        button.setOnClickListener {
            if(in_password.text.toString() != in_password2.text.toString()){
                in_password.error = "Las contraseñas no coinciden"
                in_password2.error = "Las contraseñas no coinciden"
            } else{
                if(!in_terminos.isChecked){
                    in_terminos.error = "Debes aceptar los términos y condiciones"
                    return@setOnClickListener
                }else{
                    val next_fragment = Register_2nd()
                    next_fragment.apply {
                        arguments = Bundle().apply {
                            putString("mail", in_mail.text.toString())
                            putString("name", in_name.text.toString())
                            putString("lastname", in_lastname.text.toString())
                            putString("password", in_password.text.toString())
                            putString("password2", in_password2.text.toString())
                            putBoolean("recomendaciones", in_recomendaciones.isChecked)
                        }
                    }
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.frame_layout_register, next_fragment)
                        .addToBackStack(null) // Optional: Adds transaction to back stack
                        .commit()
                }

            }
        }
    }

    private fun simulateProgress() {
        var currentProgress = progressBarController?.getProgress() ?: 0
        currentProgress = progress

        activity?.runOnUiThread{
            progressBarController?.updateProgress(currentProgress)
        }
    }

}