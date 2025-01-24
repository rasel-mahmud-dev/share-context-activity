package com.example.share.components

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.share.R

@Composable
fun FlexBox(
    modifier: Modifier = Modifier,
    horizontalSpacing: androidx.compose.ui.unit.Dp = 0.dp,
    verticalSpacing: androidx.compose.ui.unit.Dp = 0.dp,
    content: @Composable () -> Unit
) {
    Layout(
        content = content,
        modifier = modifier
    ) { measurables, constraints ->
        val horizontalSpacingPx = horizontalSpacing.roundToPx()
        val verticalSpacingPx = verticalSpacing.roundToPx()

        // Variables to track the position and size
        var rowWidth = 0
        var rowHeight = 0
        var totalHeight = 0
        var maxWidth = 0

        val placeables = measurables.map { measurable ->
            val placeable = measurable.measure(constraints)
            if (rowWidth + placeable.width > constraints.maxWidth) {
                // Move to the next row
                totalHeight += rowHeight + verticalSpacingPx
                rowWidth = 0
                rowHeight = 0
            }
            rowWidth += placeable.width + horizontalSpacingPx
            rowHeight = maxOf(rowHeight, placeable.height)
            maxWidth = maxOf(maxWidth, rowWidth)
            placeable
        }

        totalHeight += rowHeight // Add the last row height

        layout(width = constraints.maxWidth, height = totalHeight) {
            var xPosition = 0
            var yPosition = 0
            rowWidth = 0
            rowHeight = 0

            placeables.forEach { placeable ->
                if (rowWidth + placeable.width > constraints.maxWidth) {
                    // Move to the next row
                    xPosition = 0
                    yPosition += rowHeight + verticalSpacingPx
                    rowWidth = 0
                    rowHeight = 0
                }
                placeable.placeRelative(x = xPosition, y = yPosition)
                xPosition += placeable.width + horizontalSpacingPx
                rowWidth += placeable.width + horizontalSpacingPx
                rowHeight = maxOf(rowHeight, placeable.height)
            }
        }
    }
}