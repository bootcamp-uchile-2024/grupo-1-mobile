package com.example.plantopiapp

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.plantopiapp.clases.ProgressBarController
import com.example.plantopiapp.register_fragments.Register_1st
import com.example.plantopiapp.register_fragments.Register_2nd

class Register : AppCompatActivity(), ProgressBarController {

    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.register)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val firstFragment = Register_1st()
        val secondFragment = Register_2nd()

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frame_layout_register, firstFragment)
            commit()
        }

        progressBar = findViewById(R.id.progressBar_register)
        progressBar.progress = 0

    }

    override fun updateProgress(progress: Int) {
        progressBar.progress = progress
    }

    override fun getProgress(): Int{
        return progressBar.progress
    }

    fun onBackPressed(view: View) {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack() // Go back to the previous fragment
        } else {
            finish() // Exit the activity if no fragments are in the back stack
        }
    }
}


