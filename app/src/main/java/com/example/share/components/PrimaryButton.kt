package com.example.share.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.share.ui.theme.PoppinsFontFamily
import com.example.share.ui.theme.fontAwesome


@Composable
fun PrimaryButton(
    onClick: () -> Unit,
    label: String,
    icon: String?,
    px: Dp? = null,
    py: Dp? = null,
    radius: Dp? = null
) {

    Button(
        modifier = Modifier,
        onClick = {
            onClick()
        },
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF53308F)),
        contentPadding = PaddingValues(
            start = px ?: 20.dp,
            top = py ?: 16.dp,
            end = px ?: 20.dp,
            bottom = py ?: 16.dp
        ),
        shape = RoundedCornerShape(radius ?: 16.dp)
    ) {

        if (icon != null) {
            Text(
                modifier = Modifier,
                text = icon,
                fontFamily = fontAwesome,
                fontSize = 20.sp,
                color = Color.White
            )
            Spacer(Modifier.width(8.dp))
        }


        Text(
            fontFamily = PoppinsFontFamily,
            text = label,
            color = Color.White,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}