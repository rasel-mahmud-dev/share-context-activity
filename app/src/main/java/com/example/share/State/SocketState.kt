package com.example.share.State

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import java.util.Date


data class SocketStateData(
    val isConnected: Boolean,
    val lastPing: Date
)


object SocketState {
    var selectedApp by mutableStateOf<SocketStateData?>(null)
        private set

    fun setActiveState(app: SocketStateData) {
        Log.d("update state", app.lastPing.toString())
        selectedApp = app
    }
}