package com.example.plantopiapp

import android.os.Bundle
import android.widget.ProgressBar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.plantopiapp.recoverPass_fragments.RecuperaPass_1st
import com.example.plantopiapp.register_fragments.Register_1st
import com.example.plantopiapp.register_fragments.Register_2nd

class RecuperaPass : AppCompatActivity() {

    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_recupera_pass)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.recupera_pass)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val firstFragment = RecuperaPass_1st()

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frame_layout_recupera, firstFragment)
            commit()
        }

        progressBar = findViewById(R.id.progressBar_recupera)
        progressBar.progress = 0
    }
}