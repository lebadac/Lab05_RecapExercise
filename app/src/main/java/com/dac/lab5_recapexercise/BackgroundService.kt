package com.dac.lab5_recapexercise

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

class BackgroundService : Service() {
    // Run background work on a separate thread to avoid blocking the main thread
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Thread {
            for (i in 1..10) {
                Log.d("BackgroundService", "Count: $i")
                Thread.sleep(1000)
            }
            stopSelf()
        }.start()
        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? = null
}
