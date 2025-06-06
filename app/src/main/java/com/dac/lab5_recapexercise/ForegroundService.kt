package com.dac.lab5_recapexercise

import android.app.*
import android.content.Intent
import android.os.*
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat

class ForegroundService : Service() {

    // Called when the service is first created. Sets up the notification channel and starts the foreground service.
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate() {
        super.onCreate()
        val channel = NotificationChannel("service_channel", "Foreground Service", NotificationManager.IMPORTANCE_DEFAULT)
        getSystemService(NotificationManager::class.java).createNotificationChannel(channel)

        val notification = NotificationCompat.Builder(this, "service_channel")
            .setContentTitle("Foreground Service")
            .setContentText("Running...")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .build()

        startForeground(1, notification)
    }

    // Called when the service is started. Runs a background thread that logs a count every second for 10 seconds.
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Thread {
            for (i in 1..10) {
                Log.d("ForegroundService", "Count: $i")
                Thread.sleep(1000)
            }
            stopSelf()
        }.start()
        return START_NOT_STICKY
    }

    // Required override for bound services. Returns null since this is a started service, not a bound one.
    override fun onBind(intent: Intent?): IBinder? = null
}
