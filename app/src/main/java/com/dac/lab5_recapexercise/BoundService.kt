package com.dac.lab5_recapexercise

import android.app.Service
import android.content.Intent
import android.os.*
import kotlinx.coroutines.*

class BoundService : Service() {

    private val binder = LocalBinder()
    private var count = 0

    inner class LocalBinder : Binder() {
        fun getService(): BoundService = this@BoundService
    }

    override fun onBind(intent: Intent?): IBinder {
        startCounting()
        return binder
    }

    private fun startCounting() {
        CoroutineScope(Dispatchers.Default).launch {
            while (true) {
                delay(1000)
                count++
            }
        }
    }

    fun getCurrentCount(): Int = count
}
