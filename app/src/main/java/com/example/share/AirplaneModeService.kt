package com.example.share

import android.annotation.SuppressLint
import android.app.Service
import android.content.Intent
import android.content.IntentFilter
import android.os.IBinder
import androidx.core.app.NotificationCompat

class AirplaneModeService : Service() {
    private val airplaneModeReceiver = AirPlaneModeReceiver()

    @SuppressLint("ForegroundServiceType")
    override fun onCreate() {
        super.onCreate()

        // Register the receiver dynamically
        val intentFilter = IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED)
        registerReceiver(airplaneModeReceiver, intentFilter)

        // Start the service in the foreground
//        val notification = NotificationCompat.Builder(this, "airplane_mode_channel")
//            .setContentTitle("Airplane Mode Listener")
//            .setContentText("Listening for airplane mode changes")
//            .setSmallIcon(R.drawable.ic_launcher_foreground)
//            .build()
//
//        startForeground(1, notification)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(airplaneModeReceiver)
    }

    override fun onBind(intent: Intent?): IBinder? = null
}
