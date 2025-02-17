package com.example.share.screens

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.share.R
import com.example.share.components.CursorItem
import com.example.share.components.PrimaryButton
import com.example.share.database.StateDatabaseHelper
import com.example.share.ui.theme.PoppinsFontFamily
import com.example.share.ui.theme.cursorColors


data class MouseCursor(
    val name: String, val iconUri: Uri? = null, val defaultIconRes: Int? = null
)

data class ColorEntry(val name: String, val value: Color)


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CursorStylesScreen(applicationContext: Context, navHostController: NavHostController) {


    val db = StateDatabaseHelper.getInstance(applicationContext)
    val cursor = db.getState("cursor")

    val cursorInt = cursor?.toInt() ?: 0

    var mouseCursors by remember {
        mutableStateOf(
            listOf(
                MouseCursor("Default Pointer", defaultIconRes = R.drawable.ic_mouse_pointer),
                MouseCursor("Hand Pointer", defaultIconRes = R.drawable.ic_mac_mouse_pointer),
                MouseCursor("Hand Pointerj", defaultIconRes = R.drawable.ic_mac_mouse_default_pointer),
            )
        )
    }


    // File picker launcher
    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent(),
            onResult = { uri: Uri? ->
                uri?.let {
                    // Add uploaded cursor to the list
                    mouseCursors = mouseCursors + MouseCursor(name = "Custom Cursor", iconUri = it)
                }
            })

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

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 0.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = PoppinsFontFamily,
                    text = "Select your cursor.",
                    color = Color.White,
                )

                PrimaryButton(label = "Upload Cursor", icon = "\uf0ee", onClick = {})

            }

            mouseCursors.forEach { cursor ->
                Column(Modifier.padding(0.dp, 12.dp)) {

                    Text(
                        text = cursor.name,
                        color = Color(0xFFE0E0E0),
                        fontFamily = PoppinsFontFamily,
                        fontWeight = FontWeight.Medium,
                        style = MaterialTheme.typography.titleLarge
                    )

                    Spacer(Modifier.padding(6.dp))

                    FlowRow(
                        modifier = Modifier.padding(0.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                    ) {

                        cursorColors.forEach { color ->
                            CursorItem(cursor = cursor, selectedItem=cursorInt, color = color.value, onClickCursor={
                                db.saveState("cursor", it.toString())
                            })
                        }
                    }
                }
            }
        }
    }
}
