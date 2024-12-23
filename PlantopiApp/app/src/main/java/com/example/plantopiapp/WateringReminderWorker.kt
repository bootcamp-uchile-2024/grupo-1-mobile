package com.example.plantopiapp

import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters

class WateringReminderWorker(appContext: Context, workerParams: WorkerParameters) :
    Worker(appContext, workerParams) {

    override fun doWork(): Result {
        // Retrieve input data
        val plantName = inputData.getString("plantName") ?: "Plant"
        val reminderTime = inputData.getString("reminderTime") ?: ""

        // Send notification
        sendNotification(plantName, reminderTime)

        return Result.success()
    }

    private fun sendNotification(plantName: String, reminderTime: String) {
        val notificationId = System.currentTimeMillis().toInt()

        val notification = NotificationCompat.Builder(applicationContext, "watering_reminders")
            .setSmallIcon(R.drawable.ic_cuidado_bajo)
            .setContentTitle("Riega tu $plantName")
            .setContentText("Es momento de regar $plantName! Recordatorio para la fecha y hora $reminderTime.")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        val notificationManager = NotificationManagerCompat.from(applicationContext)
        notificationManager.notify(notificationId, notification)
    }
}
