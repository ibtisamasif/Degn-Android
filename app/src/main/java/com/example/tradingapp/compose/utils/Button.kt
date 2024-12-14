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
import com.example.tradingapp.ui.theme.Purple

@Composable
fun CustomizedButton(text: String,paddingBottom: Int, icon: Int?,onButtonClick: () -> Unit) {
    Button(
        onClick = {onButtonClick()},
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .padding(bottom = paddingBottom.dp)
            .fillMaxWidth()
            .height(48.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Purple),
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

