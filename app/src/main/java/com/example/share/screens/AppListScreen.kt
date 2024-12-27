package com.example.share.screens

import PackageList
import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun AppListScreen(applicationContext: Context, navHost: NavHostController){
    Column(modifier = Modifier.padding(8.dp, 0.dp)) {
        PackageList(context = applicationContext)
        Button(onClick = {
            navHost.navigate("choose-app")
        }) {
            Text("Add")
        }
    }
}