package com.example.share.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.share.R
import com.example.share.screens.ColorEntry

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)

val PoppinsFontFamily = FontFamily(
    Font(R.font.poppins_light, FontWeight.Light),
    Font(R.font.poppins_regular, FontWeight.Normal),
    Font(R.font.poppins_medium, FontWeight.Medium),
    Font(R.font.poppins_semibold, FontWeight.SemiBold),
    Font(R.font.poppins_bold, FontWeight.Bold),
    Font(R.font.poppins_black, FontWeight.Black),
)

val fontAwesome = FontFamily(
    Font(R.font.fa_regular_400)
)


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
