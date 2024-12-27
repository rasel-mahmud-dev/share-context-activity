package com.example.share

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage


data class NewClip(
    val userId: String,
    val content: String,
    val createdAt: String,
    val copiedAt: String,
    val receiverDeviceIds: List<String>,
    val senderDeviceId: String,
    val _id: String
)

data class AddClipApiResponse(
    val clip: NewClip?
)

class ShareActivity<ResolveInfo> : ComponentActivity() {

    private var receivedLink: String? = null

    val context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        handleIncomingIntent(intent)
        Log.d("on-create", "oncreate")
        setContent {
            MyAppUI(receivedLink)
        }
    }

    private fun handleIncomingIntent(intent: Intent) {
        if (Intent.ACTION_SEND == intent.action && intent.type == "text/plain") {
            val text = intent.getStringExtra(Intent.EXTRA_TEXT)
            text?.let {
                receivedLink = it
            }
        }
    }

    override fun onResume() {
        super.onResume()
        intent?.let { handleIncomingIntent(it) }
    }
}


@Composable
fun MyAppUI(link: String?) {
    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(true) }
    val packageManager = context.packageManager


    fun handleClose() {
        showDialog = false
        (context as Activity).finish()
    }


    val hardcodedPackages = listOf(
        "com.google.android.keep",
        "mark.via.gp",
        "com.brave.browser",
        "com.android.chrome"
    )


    val shareableApps = hardcodedPackages.mapNotNull { packageName ->
        try {
            val appInfo = packageManager.getApplicationInfo(packageName, 0)
            Log.d("AppFound", "App found: $packageName")
            packageName to appInfo
        } catch (e: PackageManager.NameNotFoundException) {
            Log.d("AppNotFound", "App not found: $packageName")
            null
        }
    }

    Log.d("link", link.toString())

    AlertDialog(
        onDismissRequest = { handleClose() },
        title = { Text(text = "Send To Devices") },
        text = {
            Column {
                Text("Link received: ${link}")

                Text(
                    text = "Select an app to share:",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                LazyColumn {
                    items(shareableApps) { app ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                                .clickable {
                                    val shareIntent = Intent(Intent.ACTION_SEND).apply {
                                        type = "text/plain"
                                        putExtra(Intent.EXTRA_TEXT, link ?: "No link available")
                                        setPackage(app.first)
                                    }
                                    context.startActivity(shareIntent)
                                    handleClose()
                                },
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            val context = LocalContext.current
                            val packageManager = context.packageManager
                            val iconDrawable: Drawable =
                                packageManager.getApplicationIcon(app.first)

                            AsyncImage(
                                model = iconDrawable,
                                contentDescription = "App Icon for ${app.first}",
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = packageManager.getApplicationLabel(app.second).toString())
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