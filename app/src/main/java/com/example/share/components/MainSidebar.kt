package com.example.share.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController


data class SidebarItem(
    val name: String,
    val route: String,
    val icon: String,
    val id: String,
)

val sidebarItem = listOf(
    SidebarItem("Home", "home", "sdf", "123"),
    SidebarItem("Pc Connection", "pc-connection", "s,df", "123"),
    SidebarItem("Cursor Styles", "cursor-styles", "sdf", "123"),
    SidebarItem("About", "about", "sdf", "123"),
)

@Composable
fun MainSideBar(navHostController: NavHostController) {
    Column(
        modifier = Modifier
            .width(300.dp)
            .fillMaxHeight()
            .background(Color(0xFF53308F))
            .padding(0.dp)
    ) {


        Row(modifier = Modifier.padding(20.dp)) {
            Text(
                fontSize = 30.sp,
                text = "Android Control",
                color = Color.White,
                style = MaterialTheme.typography.labelLarge
            )
        }


        // Buttons
//        Button(
//            onClick = {
//                // Action for Add
//            },
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            Text("Add")
//        }
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        Button(
//            onClick = {
//                // Action for Start
//            },
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            Icon(
//                imageVector = Icons.Default.PlayArrow,
//                contentDescription = "Start Icon",
//                modifier = Modifier.size(24.dp)
//            )
//            Spacer(modifier = Modifier.width(8.dp))
//            Text("Start")
//        }
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        Button(
//            onClick = {
//                // Action for Stop
//            },
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            Icon(
//                imageVector = Icons.Default.PlayArrow,
//                contentDescription = "Stop Icon",
//                modifier = Modifier.size(24.dp)
//            )
//            Spacer(modifier = Modifier.width(8.dp))
//            Text("Stop")
//        }
//
//        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(sidebarItem) { el ->
                ListItem(
                    text = el.name,
                    onClick = {
                        navHostController.navigate(el.route)
                    }
                )
            }
        }
    }
}


@Composable
fun ListItem(text: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(6.dp)
            .background(Color(0xFF6849A0), shape = RoundedCornerShape(8.dp))
            .padding(14.dp)
    ) {
        Text(
            text = text,
            color = Color.White,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}
