package com.dac.lab5_recapexercise

import android.os.AsyncTask
import android.widget.TextView

// AsyncTask to simulate a delay and update a TextView with the result
class MyAsyncTask(private val resultView: TextView) : AsyncTask<Int, Void, String>() {

    // This runs on a background thread and simulates a delay
    override fun doInBackground(vararg params: Int?): String {
        Thread.sleep((params[0] ?: 1) * 1000L) // Sleep for given seconds (default 1 if null)
        return "AsyncTask done after ${params[0]} seconds"
    }

    // This runs on the UI thread after background task completes
    override fun onPostExecute(result: String?) {
        resultView.text = result // Update the TextView with the result message
    }
}
