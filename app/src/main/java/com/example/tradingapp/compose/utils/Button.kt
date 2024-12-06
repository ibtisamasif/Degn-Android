package com.example.tradingapp.compose.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.tradingapp.ui.theme.Green

@Composable
fun CustomizedButton(text: String, icon: Int?) {
    Button(
        onClick = { },
        modifier = Modifier
            .padding(horizontal = 64.dp)
            .padding(bottom = 64.dp)
            .fillMaxWidth()
            .height(48.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Green),
        shape = RoundedCornerShape(12.dp)
    ) {
        if (icon != null) {
            Image(
                painter = painterResource(id = icon),
                contentDescription = null,
                modifier = Modifier.padding(end = 8.dp)
            )
        }
        Text(text = text)
    }
}

