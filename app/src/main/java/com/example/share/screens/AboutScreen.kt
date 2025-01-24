package com.example.share.screens

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun AboutScreen(applicationContext: Context, navHostController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF202020), Color(0xFF53308F))
                )
            )
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Text(
                text = "About This Application",
                color = Color.White,
                fontSize = 24.sp,
                style = MaterialTheme.typography.headlineMedium
            )

            Text(
                text = "This application is designed to enhance user productivity by providing a clean and intuitive interface.\n\n" +
                        "Explore its features and enjoy a seamless experience tailored to your needs.\n\n" +
                        "Explore its features and enjoy a seamless experience tailored to your needs.\n\n" +
                        "Explore its features and enjoy a seamless experience tailored to your needs." +
                        "Explore its features and enjoy a seamless experience tailored to your needs." +
                        "Explore its features and enjoy a seamless experience tailored to your needs.\n\n" +
                        "Explore its features and enjoy a seamless experience tailored to your needs." +
                        "Explore its features and enjoy a seamless experience tailored to your needs." +
                        "Explore its features and enjoy a seamless experience tailored to your needs." +
                        "Explore its features and enjoy a seamless experience tailored to your needs." +
                        "Explore its features and enjoy a seamless experience tailored to your needs." +
                        "Explore its features and enjoy a seamless experience tailored to your needs." +
                        "Explore its features and enjoy a seamless experience tailored to your needs.\n\n" +
                        "Explore its features and enjoy a seamless experience tailored to your needs." +
                        "Explore its features and enjoy a seamless experience tailored to your needs." +
                        "Explore its features and enjoy a seamless experience tailored to your needs." +
                        "Explore its features and enjoy a seamless experience tailored to your needs." +
                        "Explore its features and enjoy a seamless experience tailored to your needs." +
                        "Explore its features and enjoy a seamless experience tailored to your needs." +
                        "Explore its features and enjoy a seamless experience tailored to your needs." +
                        "Explore its features and enjoy a seamless experience tailored to your needs." +
                        "Explore its features and enjoy a seamless experience tailored to your needs." +
                        "Explore its features and enjoy a seamless experience tailored to your needs." +
                        "Explore its features and enjoy a seamless experience tailored to your needs." +
                        "Explore its features and enjoy a seamless experience tailored to your needs." +
                        "Explore its features and enjoy a seamless experience tailored to your needs.\n\n" +
                        "We aim to deliver fast, reliable, and secure solutions to meet your goals.",
                color = Color(0xFFE0E0E0),
                fontSize = 16.sp,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )



            // Features Section
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0x16FFFFFF))
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Key Features:",
                        color = Color.White,
                        fontSize = 18.sp,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = "• Fast and reliable performance",
                        color = Color(0xFFE0E0E0),
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = "• Intuitive user interface",
                        color = Color(0xFFE0E0E0),
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = "• Secure and private data handling",
                        color = Color(0xFFE0E0E0),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }

            // Back Button
            Button(
                onClick = { navHostController.popBackStack() },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF53308F)),
                modifier = Modifier.padding(top = 32.dp)
            ) {
                Text(
                    text = "Back",
                    color = Color.White,
                    fontSize = 16.sp
                )
            }
        }
    }
}
