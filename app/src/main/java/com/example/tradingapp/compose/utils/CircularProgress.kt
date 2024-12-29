package com.example.tradingapp.compose.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.tradingapp.ui.theme.Purple

@Composable
fun CircularProgress(){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0x66000000))
            .clickable(enabled = false) { },
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            color = Purple,
            modifier = Modifier.size(32.dp)
        )
    }
}