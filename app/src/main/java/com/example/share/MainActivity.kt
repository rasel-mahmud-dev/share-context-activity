package com.example.share

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.DisplayMetrics
import android.util.Log
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.example.share.State.AppState
import com.example.share.State.SocketState
import com.example.share.State.SocketStateData
import com.example.share.ui.theme.ShareTheme
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.ServerSocket
import java.net.Socket
import java.net.UnknownHostException
import java.util.Date
import kotlin.concurrent.thread


class MainActivity : ComponentActivity() {


    private val port = 12345
    private var serverSocket: ServerSocket? = null
    private var airPlaneModeReceiver = AirPlaneModeReceiver()

    fun sendMouseClickEvent(x: Int, y: Int) {
        val intent = Intent("com.example.share.CLICK_MOUSE").apply {
            putExtra("x", x)
            putExtra("y", y)
        }
        Log.d("SEnd mounse sevent", "${x} ${y}")
        sendBroadcast(intent) // Send broadcast to the service
    }

    fun sendMouseMoveEvent(x: Int, y: Int) {
        val intent = Intent("com.example.share.MOVE_MOUSE").apply {
            putExtra("x", x)
            putExtra("y", y)
        }
        Log.d("SEnd mounse sevent", "${x} ${y}")
        sendBroadcast(intent) // Send broadcast to the service
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.getInsetsController(window, window.decorView).apply {
            hide(WindowInsetsCompat.Type.systemBars())
            systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }


//        registerReceiver(airPlaneModeReceiver, IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED))

        AppState.loadData(applicationContext)
        enableEdgeToEdge()
        setContent {
            ShareTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Log.d("innerPadding", innerPadding.toString())
                    Column(Modifier.padding(0.dp)) {

                        Column {
                            Button(onClick = {
                                sendMouseClickEvent(150, 250)
                            }) {
                                Text("Click Here")
                            }
                        }


                        Row  {

                            Button(onClick = {
                                startConnectionFromServer()
                            }) {
                                Text("Start")
                            }

                            Button(onClick = {
                                disconnectFromServer()
                            }) {
                                Text("Disconnect")
                            }
                        }


                        AppNavigation(applicationContext)
                    }
                }
            }
        }

//        startTcpClient()
    }

    private fun getDeviceInfo(): String {
        val displayMetrics = DisplayMetrics()

        // Use the WindowManager to get display metrics
        val windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        windowManager.defaultDisplay.getMetrics(displayMetrics)

        val screenWidth = displayMetrics.widthPixels
        val screenHeight = displayMetrics.heightPixels

        // Get Android ID
        val androidId = Settings.Secure.getString(
            contentResolver,
            Settings.Secure.ANDROID_ID
        )

        // Combine information
        return "ScreenWidth: $screenWidth, ScreenHeight: $screenHeight, AndroidID: $androidId"
    }

    private fun startTcpServer() {
        thread {
            try {
                if (serverSocket == null || serverSocket?.isClosed == true) {
                    // Create a new ServerSocket if none exists or it's closed
                    serverSocket = ServerSocket(port)
                    Log.d("TCPServer", "Server started on port $port, waiting for connection...")
                }

                // Accept client connections only once
                val socket = serverSocket?.accept()
                Log.d("TCPServer", "Client connected!")

                // Set up input stream
                val inputStream = BufferedReader(InputStreamReader(socket?.getInputStream()))

                val outputStream = OutputStreamWriter(socket?.getOutputStream())

                // Get device information
                val deviceInfo = getDeviceInfo()

                // Send device information to the client
                outputStream.write("$deviceInfo\n")
                outputStream.flush()
                Log.d(
                    "TCPServer", "Device info sent to client: $deviceInfo"
                )

                while (true) {
                    val message = inputStream.readLine() // Reads a complete line of text

                    if (message != null) {
                        Log.d("TCPServer", "Received message: $message")

                        try {
                            // Try to parse the message as an integer
                            Log.d("Int msg", message)

                            if (message == "1\n") {
                                Log.d("messageInt is 1", message)

                                SocketState.setActiveState(
                                    SocketStateData(
                                        true,
                                        Date()
                                    )
                                )
                            } else {
                                // here message is "cur:1000,399"
                                if (message.startsWith("cur") || message.startsWith("cli")) {
                                    val coordinates = message.substring(4)
                                    val parts = coordinates.split(",")
                                    // Parse the coordinates into integers
                                    val x = parts[0].toIntOrNull()
                                        ?: 0  // Default to 0 if parsing fails
                                    val y = parts[1].toIntOrNull()
                                        ?: 0  // Default to 0 if parsing fails

                                    if (message.startsWith("cli")) {
                                        sendMouseClickEvent(x, y)
                                    }
                                    if (message.startsWith("cur")) {
                                        sendMouseMoveEvent(x, y)
                                    }

                                }
                            }
                        } catch (e: NumberFormatException) {
                            Log.e("TCPServer", "Received message is not an integer: $message")
                        }
                    }
                }

                // Close the socket after communication ends
//                socket?.close()
//                Log.d("TCPServer", "Connection closed.")

            } catch (e: Exception) {
                Log.e("TCPServer", "Error: ${e.message}. Retrying...")
                try {
                    serverSocket?.close() // Close the existing server socket if it failed
                } catch (closeException: Exception) {
                    Log.e("TCPServer", "Error closing socket: ${closeException.message}")
                }
                // Retry after a short delay
                Thread.sleep(1000) // Retry every 1 second
            }
        }
    }

    private fun startTcpClient() {
        thread {
            var socket: Socket? = null
            while (true) {
                try {
                    val serverAddress = "192.168.0.148"
                    val serverPort = 12345

                    socket = Socket(serverAddress, serverPort)
                    Log.d("TCPClient", "Connected to server at $serverAddress:$serverPort")

                    val inputStream = BufferedReader(InputStreamReader(socket.getInputStream()))
                    val outputStream = OutputStreamWriter(socket.getOutputStream())

                    val deviceInfo = inputStream.readLine()
                    Log.d("TCPClient", "Device info received from server: $deviceInfo")

                    while (socket.isConnected) {
                        val serverResponse = inputStream.readLine()
                        if (serverResponse != null) {
                            Log.d("TCPClient", "Received from server: $serverResponse")
                        } else {
                            Log.d("TCPClient", "Server closed connection, retrying...")
                            break
                        }
                    }

                    socket.close()
                    Log.d("TCPClient", "Connection closed by server.")
                    retryConnection()

                } catch (e: UnknownHostException) {
                    Log.e("TCPClient", "Unknown host: ${e.message}")
                    retryConnection()
                } catch (e: Exception) {
                    Log.e("TCPClient", "Error: ${e.message}")
                    retryConnection()
                }
            }
        }
    }

    private fun retryConnection() {
        try {
            Log.d("TCPClient", "Retrying connection in 5 seconds...")
            Thread.sleep(5000)
        } catch (e: InterruptedException) {
            Log.e("TCPClient", "Error while waiting to retry: ${e.message}")
        }
    }

    private fun disconnectFromServer() {
          val serviceIntent = Intent(this, TcpClientService::class.java)

        stopService(serviceIntent)
    }

    private fun startConnectionFromServer() {
          val serviceIntent = Intent(this, TcpClientService::class.java)

        startService(serviceIntent)
    }


    override fun onDestroy() {
        super.onDestroy()
//        unregisterReceiver(airPlaneModeReceiver)
    }

    override fun onPause() {
        super.onPause()
        Log.d("Main", "OnPause")
        startService(Intent(this, AirplaneModeService::class.java))
    }

}

enum class ChatMapping(val mappedValue: String) {
    ONE("1"), TWO("2"), THREE("3"), FOUR("4"), FIVE("5"),
    SIX("6"), SEVEN("7"), EIGHT("8"), NINE("9");

    companion object {
        // Function to get the mapped string based on integer value
        fun fromInt(num: Int): String {
            return when {
                num in 1..9 -> num.toString() // 1 to 9 map to themselves
                num in 10..35 -> ('A' + (num - 10)).toString() // 10 to 35 map to A to Z
                else -> throw IllegalArgumentException("Invalid number: $num")
            }
        }
    }
}
