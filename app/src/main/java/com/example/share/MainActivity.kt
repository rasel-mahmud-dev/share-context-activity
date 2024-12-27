package com.example.share

import PackageList
import android.R
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.ContextMenu.ContextMenuInfo
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.share.ui.theme.ShareTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ShareTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(Modifier.padding(innerPadding)) {
                        Greeting(applicationContext = applicationContext)
                    }
                }
            }
        }
    }

    override fun onCreateContextMenu(menu: ContextMenu, v: View, menuInfo: ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        // Add custom actions to the context menu
        menu.add(0, v.id, 0, "Share Link")
        menu.add(0, v.id, 0, "Copy Text")
        menu.add(0, v.id, 0, "My Custom Action")
    }
}

@Composable
fun Greeting(applicationContext: Context) {
    val packageManager = applicationContext.packageManager

    var showSelectApp by remember { mutableStateOf(false) }

    fun handleClose() {
        showSelectApp = false
    }


    fun getInstalledApplications(): List<Pair<String, String>> {
        val appList = mutableListOf<Pair<String, String>>()


        val installedApplications =
            packageManager.getInstalledApplications(PackageManager.GET_META_DATA)
        for (appInfo in installedApplications) {
            val appName = packageManager.getApplicationLabel(appInfo).toString()
            val packageName = appInfo.packageName
            appList.add(appName to packageName)
        }
        return appList
    }

    val installedApps = getInstalledApplications()

    Column(modifier = Modifier.padding(8.dp, 0.dp)) {
        PackageList(context = applicationContext)
        Button(onClick = {
            showSelectApp = true
        }) {
            Text("Add")
        }
    }

    if (showSelectApp) {
        AlertDialog(
            onDismissRequest = { handleClose() },
            title = { Text(text = "Send To Devices") },
            text = {
                Column {

                    Text(
                        text = "Select an app to share:",
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )

                    LazyColumn {
                        items(installedApps) { app ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp)
                                    .clickable {
                                    },
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                val iconDrawable: Drawable? = try {
                                    packageManager.getApplicationIcon(app.second)
                                } catch (e: Exception) {
                                    null
                                }

                                if (iconDrawable != null) {
                                    AsyncImage(
                                        model = iconDrawable,
                                        contentDescription = "App Icon for ${app.first}",
                                        modifier = Modifier.size(24.dp)
                                    )
                                } else {
                                    Image(
                                        painter = painterResource(id = R.drawable.sym_def_app_icon),
                                        contentDescription = "Fallback App Icon",
                                        modifier = Modifier.size(24.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(text = app.first)
                            }
                        }
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = { handleClose() }
                ) {
                    Text("Close")
                }
            }
        )
    }

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ShareTheme {
        Greeting(LocalContext.current)
    }
}