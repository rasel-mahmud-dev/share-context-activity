package com.example.share.screens

import PackageList
import android.content.Context
import android.content.Intent
import android.provider.Settings
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavHostController
import com.example.share.State.SocketState
import com.example.share.utils.getLocalIPv4Address
import kotlinx.coroutines.delay
import java.time.Duration
import java.time.Instant
import java.util.Date
import kotlin.math.abs

@Composable
fun AppListScreen(applicationContext: Context, navController: NavHostController) {

    Column(
        modifier = Modifier
            .padding(16.dp)
            .background(Color(0xFF171717))
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            LastPingText()

            Text(
                text = "Shareable Apps",
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            Button(onClick = {
                navController.navigate("choose-app")
            }) {
                Text("Add")
            }
        }

        PackageList(context = applicationContext)

    }


}


fun formatLastPing(lastPing: Date): String {
    val now = Instant.now()
    val lastPingInstant = lastPing.toInstant()

    val duration = Duration.between(lastPingInstant, now)

    return when {
        duration.seconds < 1 -> {
            val millis = duration.toMillis()
            "${millis}ms"
        }

        duration.seconds < 60 -> "${abs(duration.seconds)}s"
        duration.toMinutes() < 60 -> "${abs(duration.toMinutes())}min"
        duration.toHours() < 24 -> "${abs(duration.toHours())}h"
        else -> "${abs(duration.toDays())}d"
    }
}

@Composable
fun LastPingText() {
    var text by remember { mutableStateOf("") }

    val context = LocalContext.current
    val timeFormatted = remember { mutableStateOf("") }

    val lastPing = SocketState.selectedApp?.lastPing ?: Date()

    LaunchedEffect(lastPing) {
        timeFormatted.value = formatLastPing(lastPing)

//        while (true) {
//            timeFormatted.value = formatLastPing(lastPing)
//            delay(5000)
//        }
    }

    Column {
        Text(
            text = "Last Ping: ${timeFormatted.value} ago",
            fontWeight = FontWeight.Bold,
            fontSize = 25.sp,
            modifier = Modifier.padding(vertical = 8.dp)
        )


        Column {
            Button(onClick = {
//                val intent = Intent(Settings.ACTION_INPUT_METHOD_SETTINGS)
//                context.startActivity(intent)


                // Example of sending socket data - assuming you received x and y
                val x = 150 // Example x-coordinate
                val y = 250 // Example y-coordinate

                // Send the broadcast to MouseControlService
                val intent = Intent("com.example.mousecontrolservice.MOVE_MOUSE")
                intent.putExtra("x", x)
                intent.putExtra("y", y)
                context.sendBroadcast(intent)


            }) {
                Text("Switch this")
            }

            Text("hhhhhhhhhhhhhhh")
            getLocalIPv4Address()?.let {
                Row {
                    Text(it)
                    Text("::")
                    Text("12345")
                }
            }

            TextField(
                value = text,
                onValueChange = { text = it },
                label = { Text("Enter text") },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }

}