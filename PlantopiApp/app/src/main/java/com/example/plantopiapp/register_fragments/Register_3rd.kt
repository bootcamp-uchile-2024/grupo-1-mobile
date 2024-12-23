package com.example.plantopiapp.register_fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import com.example.plantopiapp.clases.ProgressBarController
import com.example.plantopiapp.R

class Register_3rd : Fragment() {

    private var progressBarController: ProgressBarController? = null
    private var progress = 80

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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register_3rd, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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

        // Find views
        val button = view.findViewById<Button>(R.id.continuar_encuesta)

        val option_1: LinearLayout = view.findViewById(R.id.ly_option_1)
        val option_2: LinearLayout = view.findViewById(R.id.ly_option_2)
        val option_3: LinearLayout = view.findViewById(R.id.ly_option_3)

        var selectedOption: LinearLayout? = null

        var expertiz: Int = 0

        option_1.setOnClickListener {
            selectedOption?.isSelected = false // Deselect previously selected
            option_1.isSelected = true
            selectedOption = option_1
            expertiz = 1
        }

        option_2.setOnClickListener {
            selectedOption?.isSelected = false
            option_2.isSelected = true
            selectedOption = option_2
            expertiz = 2

        }

        option_3.setOnClickListener {
            selectedOption?.isSelected = false
            option_3.isSelected = true
            selectedOption = option_3
            expertiz = 3

        }

        button.setOnClickListener {

            if (selectedOption == null) {
                Toast.makeText(requireContext(), "Por favor, selecciona una opción", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else {

                // Navigate to the next fragment
                val nextFragment = Register_4th().apply {
                    arguments = Bundle().apply {
                        putString("mail", in_mail)
                        putString("name", in_name)
                        putString("lastname", in_lastname)
                        putString("password", in_password)
                        putString("run", in_run)
                        putString("telefono", in_telefono)
                        putString("direccion", in_direccion)
                        putString("comuna", in_comuna)
                        putString("codigo_postal", in_codigo_postal)
                        putInt("expertiz", expertiz)
                    }
                }

                parentFragmentManager.beginTransaction()
                    .replace(R.id.frame_layout_register, nextFragment)
                    .addToBackStack(null) // Optional: Adds transaction to back stack
                    .commit()
            }

            simulateProgress()
        }



    }


    private fun simulateProgress() {
        progressBarController?.updateProgress(progress)
    }
}
