package com.example.plantopiapp.clases

interface ProgressBarController {
    fun updateProgress(progress: Int)
    fun getProgress(): Int
}