package com.example.share.screens

import PackageList
import android.R
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.share.State.AppViewModel

@Composable
fun AppListScreen(applicationContext: Context, navHost: NavHostController, appViewModel: AppViewModel){
    Column(modifier = Modifier.padding(8.dp, 0.dp)) {
        PackageList(context = applicationContext, appViewModel)
        Button(onClick = {
            navHost.navigate("choose-app")
        }) {
            Text("Add")
        }
    }
}