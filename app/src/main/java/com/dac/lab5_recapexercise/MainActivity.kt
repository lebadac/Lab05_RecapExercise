package com.dac.lab5_recapexercise

import android.content.*
import android.os.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import android.widget.*
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    private var boundService: BoundService? = null
    private var isBound = false

    private lateinit var countText: TextView
    private lateinit var bindBtn: Button

    // ServiceConnection to manage binding and unbinding to the BoundService
    private val connection = object : ServiceConnection {
        // Called when the connection with the service has been established
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as BoundService.LocalBinder
            boundService = binder.getService()
            isBound = true
            Toast.makeText(this@MainActivity, "Service Bound", Toast.LENGTH_SHORT).show()
        }

        // Called when the connection with the service has been unexpectedly disconnected
        override fun onServiceDisconnected(name: ComponentName?) {
            isBound = false
        }
    }

    // Called when the activity is first created
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bgButton = findViewById<Button>(R.id.btnStartBackground)
        val fgButton = findViewById<Button>(R.id.btnStartForeground)
        bindBtn = findViewById<Button>(R.id.btnBindService)
        countText = findViewById(R.id.textCount)

        // Start BackgroundService when button is clicked
        bgButton.setOnClickListener {
            val intent = Intent(this, BackgroundService::class.java)
            startService(intent)
        }

        // Start ForegroundService when button is clicked
        fgButton.setOnClickListener {
            val intent = Intent(this, ForegroundService::class.java)
            ContextCompat.startForegroundService(this, intent)
        }

        // Bind to BoundService and display current count
        bindBtn.setOnClickListener {
            if (!isBound) {
                Intent(this, BoundService::class.java).also { intent ->
                    bindService(intent, connection, Context.BIND_AUTO_CREATE)
                }
            } else {
                CoroutineScope(Dispatchers.Main).launch {
                    val count = boundService?.getCurrentCount() ?: 0
                    countText.text = "Count: $count"
                }
            }
        }

        // Example using AsyncTask
        MyAsyncTask(findViewById(R.id.textAsyncResult)).execute(5)

        // Example using Kotlin Coroutines
        CoroutineScope(Dispatchers.Main).launch {
            val result = withContext(Dispatchers.Default) {
                delay(2000)
                return@withContext "Coroutine done!"
            }
            findViewById<TextView>(R.id.textCoroutineResult).text = result
        }
    }

    // Called when the activity is destroyed (e.g. user closes app)
    override fun onDestroy() {
        super.onDestroy()
        if (isBound) {
            unbindService(connection)
            isBound = false
        }
    }
}
