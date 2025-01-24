package com.example.share.screens

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController

@Composable
fun ConnectionScreen(applicationContext: Context, navHostController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF202020)),
        contentAlignment = Alignment.Center
    ) {


        Text(
            text = "Main Contenssst Aresssssssssssa",
            color = Color.White
        )
    }

}
