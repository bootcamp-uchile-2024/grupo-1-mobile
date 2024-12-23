package com.example.plantopiapp.recoverPass_fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import com.example.plantopiapp.MainActivity
import com.example.plantopiapp.clases.ProgressBarController
import com.example.plantopiapp.R
import com.example.plantopiapp.Register
import com.example.plantopiapp.register_fragments.Register_2nd

class RecuperaPass_4th : Fragment(R.layout.fragment_recupera_pass_4th) {

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

        val button = view.findViewById<Button>(R.id.button_recover4th)

        button.setOnClickListener {
            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
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