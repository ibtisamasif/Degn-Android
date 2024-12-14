package com.example.tradingapp.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.tradingapp.R

val InterThin = FontFamily(Font(R.font.inter_thin))
val InterLight = FontFamily(Font(R.font.inter_light))
val InterExtraLight = FontFamily(Font(R.font.inter_extra_light))
val InterNormal = FontFamily(Font(R.font.inter_regular))
val InterMedium = FontFamily(Font(R.font.inter_medium))
val InterSemiBold = FontFamily(Font(R.font.inter_semi_bold))
val InterBold = FontFamily(Font(R.font.inter_bold))
val InterExtraBold = FontFamily(Font(R.font.inter_extra_bold))
val InterBlack = FontFamily(Font(R.font.inter_black))

val Typography = Typography(

    //Bold
    headlineLarge = TextStyle(
        fontFamily = InterBold,
        fontSize = 30.sp,
        letterSpacing = 0.sp,
        textAlign = TextAlign.Center
    ),
    headlineMedium = TextStyle(
        fontFamily = InterBold,
        fontSize = 26.sp,
        letterSpacing = 0.sp,
        textAlign = TextAlign.Center
    ),
    headlineSmall = TextStyle(
        fontFamily = InterBold,
        fontSize = 20.sp,
        letterSpacing = 0.sp,
        textAlign = TextAlign.Center
    ),

    //Medium
    displayLarge = TextStyle(
        fontFamily = InterMedium,
        fontSize = 16.sp,
        letterSpacing = 0.sp,
        textAlign = TextAlign.Center
    ),
    displayMedium = TextStyle(
        fontFamily = InterMedium,
        fontSize = 12.sp,
        letterSpacing = 0.sp,
        textAlign = TextAlign.Center
    ),

    //Semi Bold
    titleLarge = TextStyle(
        fontFamily = InterSemiBold,
        fontSize = 36.sp,
        letterSpacing = 0.sp,
        textAlign = TextAlign.Center
    ),
    titleMedium = TextStyle(
        fontFamily = InterSemiBold,
        fontSize = 24.sp,
        letterSpacing = 0.sp,
        textAlign = TextAlign.Center
    ),
    titleSmall = TextStyle(
        fontFamily = InterSemiBold,
        fontSize = 14.sp,
        letterSpacing = 0.sp,
        textAlign = TextAlign.Center
    ),


    bodyLarge = TextStyle(
        fontFamily = InterNormal,
        fontSize = 16.sp,
        letterSpacing = 0.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = InterThin,
        fontSize = 14.sp,
        letterSpacing = 0.sp
    ),
    bodySmall = TextStyle(
        fontFamily = InterExtraLight,
        fontSize = 12.sp,
        letterSpacing = 0.sp
    ),

    //Light
    labelLarge = TextStyle(
        fontFamily = InterLight,
        fontSize = 16.sp,
        textAlign = TextAlign.Center
    ),
    labelMedium = TextStyle(
        fontFamily = InterLight,
        fontSize = 14.5.sp,
        letterSpacing = 0.sp,
        textAlign = TextAlign.Start
    ),
    labelSmall = TextStyle(
        fontFamily = InterLight,
        fontSize = 10.sp,
        letterSpacing = 0.sp
    )
)