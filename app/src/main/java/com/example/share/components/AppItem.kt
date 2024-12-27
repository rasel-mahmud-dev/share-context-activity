package com.example.share.components

import android.R
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage

@Composable
fun AppItem(app: Pair<String, String>, onPress: (String) -> Unit, packageManager: PackageManager) {
    Row(
        modifier = Modifier
            .padding(0.dp)
            .clickable {
                onPress(app.second)
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