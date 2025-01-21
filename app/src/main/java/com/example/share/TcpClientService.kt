package com.example.share

import android.annotation.SuppressLint
import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.Socket
import java.net.UnknownHostException
import kotlin.concurrent.thread

class TcpClientService : Service() {

    private lateinit var deviceId: String
    private var socket: Socket? = null
    private var isRunning = true

    private fun showToast(message: String) {
        Handler(Looper.getMainLooper()).post {
            Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
        }
    }

    fun sendMouseClickEvent(x: Float, y: Float) {
        val intent = Intent("com.example.share.CLICK_MOUSE").apply {
            putExtra("x", x)
            putExtra("y", y)
        }
        Log.d("send mouse click event", "${x} ${y}")
        sendBroadcast(intent)
    }

    fun sendMouseMoveEvent(x: Float, y: Float) {
        val intent = Intent("com.example.share.MOVE_MOUSE").apply {
            putExtra("x", x)
            putExtra("y", y)
        }
        Log.d("senc mouse move event", "${x} ${y}")
        sendBroadcast(intent) // Send broadcast to the service
    }

    @SuppressLint("HardwareIds")
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val ipv4Address = intent?.getStringExtra("ipv4Address") ?: throw Error("IPV4 required...")

        deviceId = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
        Log.d("TCPClientService", "Device ID: $deviceId")
        startTcpClient(ipv4Address)
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    private fun startTcpClient(ipAddress: String) {
        thread {

            try {


                val serverPort = 12346

                socket = Socket(ipAddress, serverPort)
                Log.d("TCPClientService", "Connected to server at $ipAddress:$serverPort")
                showToast("Connected to server at $ipAddress:$serverPort")

                val inputStream = BufferedReader(InputStreamReader(socket?.getInputStream()))
                val outputStream = OutputStreamWriter(socket?.getOutputStream())

                // Send device ID as the first message to the server
                outputStream.write("$deviceId\n")
                outputStream.flush()
                Log.d("TCPClientService", "Sent device ID: $deviceId")

                val deviceInfo = inputStream.readLine()
                Log.d("TCPClientService", "Device info received from server: $deviceInfo")

                while (socket?.isConnected == true) {
                    val message = inputStream.readLine()
                    if (message != null) {
                        Log.d("TCPClientService", "Received from server: $message")

                        if (message.startsWith("cur") || message.startsWith("cli")) {
                            val coordinates = message.substring(4)
                            val parts = coordinates.split(",")
                            val x = parts[0].toFloatOrNull() ?: 0.0
                            val y = parts[1].toFloatOrNull() ?: 0.0

                            if (message.startsWith("cli")) {
                                sendMouseClickEvent(x.toFloat(), y.toFloat())
                            }
                            if (message.startsWith("cur")) {
                                sendMouseMoveEvent(x.toFloat(), y.toFloat())
                            }

                        }


                    } else {
                        Log.d("TCPClientService", "Server closed connection, retrying...")
                        break
                    }
                }
//                retryConnection()
                socket?.close()
                showToast("Unknown host: ")
                Log.d("TCPClientService", "Connection closed by server.")

            } catch (e: UnknownHostException) {
                showToast("Unknown host: ${e.message}")
                Log.e("TCPClientService", "Unknown host: ${e.message}")
//                retryConnection()
            } catch (e: Exception) {
                showToast("Error: ${e.message}")

                Log.e("TCPClientService", "Error: ${e.message}")
//                retryConnection()

            }
        }
    }

    private fun retryConnection() {
        try {
            Log.d("TCPClientService", "Retrying connection in 5 seconds...")
            Thread.sleep(5000) // Wait for 5 seconds before retrying
//            startTcpClient()
        } catch (e: InterruptedException) {
            Log.e("TCPClientService", "Error while waiting to retry: ${e.message}")
        }
    }

    fun stopServiceAndCloseSocket() {
        isRunning = false
        socket?.close()
        stopSelf()
        Log.d("TCPClientService", "Service stopped and socket closed.")
    }

    override fun onDestroy() {
        super.onDestroy()
        stopServiceAndCloseSocket()
        Log.d("TCPClientService", "Service destroyed and resources cleaned up.")
    }
}
