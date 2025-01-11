package com.example.degn.ui.theme

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

val Green = Color(0xFF83E058)
val Red = Color(0xFFFF3D3D)
val Grey = Color(0xFFD9D9D9)
val Grey45 = Grey.copy(alpha = 0.45f)
val Purple = Color(0xFF6771EA)
val Sky = Color(0xFF97ABF8)
val OffWhite = Color(0xFFF1F1F1)

val gradient = Brush.linearGradient(
    colors = listOf(
        Color(0xFFFF2E01),
        Color(0xFFFF7B68),
        Color(0xFFF19CAB),
        Color(0xFFB55DD9),
        Color(0xFF6701FF)
    ),
    start = Offset.Zero,
    end = Offset.Infinite,
)


val LightText = Color.Black
val LightIcon = Color.Black

val DarkText = Color.White
val DarkIcon = Color.White