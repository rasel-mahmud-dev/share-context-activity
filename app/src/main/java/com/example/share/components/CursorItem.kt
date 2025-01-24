package com.example.share.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.share.screens.MouseCursor

@Composable
fun CursorItem(cursor: MouseCursor, selectedItem: Int, color: Color, onClickCursor: (url: Int)->Unit) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    Row(
        modifier = Modifier
            .background(
                Color(0x00FFFFFF), shape = RoundedCornerShape(12.dp)
            )
            .border(
                border = BorderStroke(1.dp, color.copy(alpha = 0.3f)),
                shape = RoundedCornerShape(12.dp)
            )
            .clickable(
                onClick = { cursor.defaultIconRes?.let { onClickCursor(it) } }, indication = null, interactionSource = interactionSource
            )
            .background(
                if (isPressed || selectedItem == cursor.defaultIconRes ) color.copy(alpha = 0.2f) else Color.Transparent,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(16.dp), verticalAlignment = Alignment.CenterVertically
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
