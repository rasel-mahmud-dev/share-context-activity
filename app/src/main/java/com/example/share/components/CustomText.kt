package com.example.share.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.share.ui.theme.fontAwesome


@Composable
fun CustomText(
    text: String? = null,
    color: Color? = null,
    style: TextStyle? = null,
    fontFamily: FontFamily? = null,
    fontWeight: FontWeight? = null,
    fs: TextUnit? = null,
    modifier: Modifier = Modifier,
    icon: String? = null,
    pr: Dp? = null,
    pl: Dp? = null,
    pt: Dp? = null,
    pb: Dp? = null,
    px: Dp? = null,
    py: Dp? = null,
    onClick: (() -> Unit)? = null
) {

    var fontM = fontFamily
    if (icon != null) {
        fontM = fontAwesome
    } else if (fontFamily == null) {
        fontM = FontFamily.Default
    }

    val finalModifier = modifier.then(
        Modifier
            .padding(
                start = pl ?: px ?: 0.dp,
                top = pt ?: py ?: 0.dp,
                end = pr ?: px ?: 0.dp,
                bottom = pb ?: py ?: 0.dp
            )
            .let {
                if (onClick != null) {
                    it.clickable { onClick() }
                } else {
                    it
                }
            }
    )

    Text(
        text = icon ?: text ?: "",
        color = color ?: Color.White,
        fontFamily = fontM,
        fontWeight = fontWeight,
        fontSize = fs ?: 16.sp,
        style = style ?: MaterialTheme.typography.bodyLarge,
        modifier = finalModifier
    )
}