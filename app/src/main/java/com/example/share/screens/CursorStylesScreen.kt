package com.example.share.screens

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.share.R


data class MouseCursor(
    val name: String,
    val iconUri: Uri? = null,
    val defaultIconRes: Int? = null
)

data class ColorEntry(val name: String, val value: Color)

val cursorColors = listOf(
    ColorEntry("Coral", Color(0xFFFF7F50)),
    ColorEntry("Turquoise", Color(0xFF40E0D0)),
    ColorEntry("Lavender", Color(0xFFE6E6FA)),
    ColorEntry("Plum", Color(0xFF8E4585)),
    ColorEntry("Aquamarine", Color(0xFF7FFFD4)),
    ColorEntry("Sea Green", Color(0xFF2E8B57)),
    ColorEntry("Periwinkle", Color(0xFFCCCCFF)),
    ColorEntry("Mauve", Color(0xFF8A7F8E)),
    ColorEntry("Mint Green", Color(0xFF98FF98)),
    ColorEntry("Peach Puff", Color(0xFFFFDAB9)),
    ColorEntry("Fuchsia", Color(0xFFFF00FF)),
    ColorEntry("Light Sky Blue", Color(0xFF87CEFA)),
    ColorEntry("Goldenrod", Color(0xFFDAA520)),
    ColorEntry("Dark Orchid", Color(0xFF9932CC)),
    ColorEntry("Salmon", Color(0xFFFA8072)),
    ColorEntry("Slate Blue", Color(0xFF6A5ACD)),
    ColorEntry("Light Coral", Color(0xFFF08080)),
    ColorEntry("Turquoise Blue", Color(0xFF00CED1)),
    ColorEntry("Cherry Blossom", Color(0xFFFFB7C5)),
    ColorEntry("Sunset Orange", Color(0xFFFF4500)),
    ColorEntry("Light Pink", Color(0xFFFFB6C1)),
    ColorEntry("Caribbean Green", Color(0xFF00C9A7)),
    ColorEntry("Berry", Color(0xFF9B1B30)),
    ColorEntry("Rose Quartz", Color(0xFFF7CAC9)),
    ColorEntry("Steel Blue", Color(0xFF4682B4))
)

//val poppinsFontFamily = FontFamily(
//    Font(R.font.poppins_light)
//)

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CursorStylesScreen(applicationContext: Context, navHostController: NavHostController) {
    var mouseCursors by remember {
        mutableStateOf(
            listOf(
                MouseCursor("Default Pointer", defaultIconRes = R.drawable.ic_mouse_pointer),
                MouseCursor("Hand Pointer", defaultIconRes = R.drawable.ic_mouse_pointer),
                MouseCursor("Text Select", defaultIconRes = R.drawable.ic_mouse_pointer),
                MouseCursor("Text Select", defaultIconRes = R.drawable.ic_mouse_pointer),
                MouseCursor("Crosshair", defaultIconRes = R.drawable.ic_mouse_pointer)
            )
        )
    }

    // File picker launcher
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            uri?.let {
                // Add uploaded cursor to the list
                mouseCursors = mouseCursors + MouseCursor(name = "Custom Cursor", iconUri = it)
            }
        }
    )

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


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                fontSize = 40.sp,
                fontWeight = FontWeight.Light,
//                fontFamily = poppinsFontFamily,
                text = "Select your cursor.",
                color = Color.White,
            )

            Button(
                onClick = {
                    launcher.launch("image/png")
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF53308F)),
                modifier = Modifier.padding(0.dp)
            ) {
                Text(
                    text = "Upload Custom Cursor",
                    color = Color.White,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }




        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {


            mouseCursors.forEach { cursor ->
                Column(Modifier.padding(0.dp, 12.dp)) {

                    Text(
                        text = cursor.name,
                        color = Color.White,
                        style = MaterialTheme.typography.titleLarge
                    )

                    Spacer(Modifier.padding(6.dp))

                    FlowRow(
                        modifier = Modifier.padding(0.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                    ) {

                        cursorColors.forEach { color ->
                            CursorItem(cursor = cursor, color.value)
                        }
                    }


                }
            }
        }


    }
}


@Composable
fun CursorItem(cursor: MouseCursor, color: Color) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    Row(
        modifier = Modifier
            .background(
                Color(0x00FFFFFF),
                shape = RoundedCornerShape(12.dp)
            )
            .border(
                border = BorderStroke(1.dp, color.copy(alpha = 0.3f)),
                shape = RoundedCornerShape(12.dp)
            )
            .clickable(
                onClick = { },
                indication = null,
                interactionSource = interactionSource
            )
            .background(
                if (isPressed) color.copy(alpha = 0.2f) else Color.Transparent,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        if (cursor.iconUri != null) {
//            AsyncImage(
//                model = ImageRequest.Builder(LocalContext.current)
//                    .data(cursor.iconUri)
//                    .crossfade(true)
//                    .build(),
//                contentDescription = cursor.name,
//                modifier = Modifier.size(48.dp)
//            )
        } else if (cursor.defaultIconRes != null) {
            Icon(
                painter = painterResource(id = cursor.defaultIconRes),
                contentDescription = cursor.name,
                tint = color,
                modifier = Modifier.size(20.dp)
            )
        }

//        Spacer(modifier = Modifier.width(16.dp))

        // Cursor name
//        Text(
//            text = cursor.name,
//            color = Color.White,
//            style = MaterialTheme.typography.bodyLarge
//        )
    }
}
