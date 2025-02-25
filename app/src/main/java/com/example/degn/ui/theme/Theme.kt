package com.example.degn.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = Purple,
    secondary = PurpleGrey80,
    tertiary = Pink80,
    onPrimary = Color.Black,
    background = Color.White,
    onBackground = DarkText,
    surface = Color.White,
    onSurface = DarkText,
    outline = Color.Black
)

private val LightColorScheme = lightColorScheme(
    primary = Purple,
    secondary = PurpleGrey40,
    tertiary = Pink40,
    onPrimary = Color.White,
    background = Color.Black,
    onBackground = LightText,
    surface = Color.Black,
    onSurface = LightText,
    outline = Color.White
)

@Composable
fun DegnAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicLightColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}