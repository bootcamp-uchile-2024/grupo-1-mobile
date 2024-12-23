package com.example.plantopiapp.register_fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.plantopiapp.Constants
import com.example.plantopiapp.clases.ProgressBarController
import com.example.plantopiapp.R
import com.example.plantopiapp.dataclases.Usuario
import com.example.plantopiapp.servicios.UsuarioService
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Register_2nd : Fragment(R.layout.fragment_register_2nd) {

    private var progressBarController: ProgressBarController? = null
    private var progress = 40

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

        val in_mail = arguments?.getString("mail")
        val in_name = arguments?.getString("name")
        val in_lastname = arguments?.getString("lastname")
        val in_password = arguments?.getString("password")

        simulateProgress()

        val button = view.findViewById<Button>(R.id.button_register2nd)
        button.setOnClickListener {
            val next_fragment = Register_extraData()

            next_fragment.apply {
                arguments = Bundle().apply {
                    putString("mail", in_mail)
                    putString("name", in_name)
                    putString("lastname", in_lastname)
                    putString("password", in_password)
                }
            }

            parentFragmentManager.beginTransaction()
                .replace(R.id.frame_layout_register, next_fragment)
                .addToBackStack(null) // Optional: Adds transaction to back stack
                .commit()
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