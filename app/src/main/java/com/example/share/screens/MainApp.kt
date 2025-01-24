package com.example.share.screens

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.rememberNavController
import com.example.share.AppNavigation
import com.example.share.TcpClientService
import com.example.share.components.MainSideBar

@Composable
fun MainApp(applicationContext: Context) {

    var ipv4Address by remember { mutableStateOf("192.168.0.148") }


    fun startConnectionFromServer() {
        val serviceIntent = Intent(applicationContext, TcpClientService::class.java).apply {
            putExtra("ipv4Address", ipv4Address)
        }
        applicationContext.startService(serviceIntent)
    }

    fun disconnectFromServer() {
        val serviceIntent = Intent(applicationContext, TcpClientService::class.java)
        applicationContext.stopService(serviceIntent)
    }

    val navController = rememberNavController()

    fun sendMouseClickEvent() {
        val intent = Intent("com.example.share.CLICK_MOUSE").apply {
            putExtra("x", 360.0F)
            putExtra("y", 618.22F)
        }
        applicationContext.sendBroadcast(intent)
    }

    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF101010))
    ) {

        MainSideBar(navController)

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF202020)),
            contentAlignment = Alignment.Center
        ) {

            AppNavigation(applicationContext, navController)
        }
    }

//    Column(
//        modifier = Modifier
//            .padding(16.dp)
//            .background(Color(0xFF171717))
//    ) {
//
//        TextField(
//            value = ipv4Address,
//            onValueChange = { ipv4Address = it },
//            label = { Text("Enter IPv4 Address") },
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(vertical = 8.dp),
//            singleLine = true
//        )
//
//
//
//        Button(onClick = {
//            sendMouseClickEvent()
//        }) {
//            Text("Add")
//        }
//
//
//        Box(
//            modifier = Modifier
//                .fillMaxSize(),
//            contentAlignment = Alignment.Center
//        ) {
//            Button(onClick = {
//                startConnectionFromServer()
//            }) {
//                Icon(
//                    imageVector = Icons.Default.PlayArrow,
//                    contentDescription = "Start Icon",
//                    modifier = Modifier.size(24.dp)
//                )
//                Spacer(modifier = Modifier.width(8.dp))
//                Text("Start")
//            }
//        }
//
//        Box(
//            modifier = Modifier
//                .fillMaxSize(),
//            contentAlignment = Alignment.Center
//        ) {
//            Button(onClick = {
//                disconnectFromServer()
//            }) {
//                Icon(
//                    imageVector = Icons.Default.PlayArrow,
//                    contentDescription = "Start Icon",
//                    modifier = Modifier.size(24.dp)
//                )
//                Spacer(modifier = Modifier.width(8.dp))
//                Text("Stop")
//            }
//        }

//    }
}
