package com.example.share

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class AirPlaneModeReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d("AirPlaneModeReceiver", "called")
        if (intent?.action == Intent.ACTION_AIRPLANE_MODE_CHANGED) {
            Log.d("AirPlaneModeReceiver", "chganges.")
        }
    }

}