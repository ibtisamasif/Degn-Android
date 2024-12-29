package com.example.tradingapp.compose.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tradingapp.R
import com.example.tradingapp.ui.theme.TradingAppTheme

@Composable
fun Title(title: String, isBottomSheet: Boolean = false, onBackPress: () -> Unit) {
    TradingAppTheme {
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            if (!isBottomSheet) {
                Box(modifier = Modifier.align(Alignment.CenterStart)) {
                    IconButton(onClick = onBackPress) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowLeft,
                            contentDescription = "Back",
                            modifier = Modifier.size(64.dp)
                        )
                    }
                }
            }
            Box(modifier = Modifier.align(Alignment.Center)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium.copy(fontSize = 18.sp),
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            if (isBottomSheet) {
                Box(modifier = Modifier.align(Alignment.CenterEnd).padding(start = 12.dp, end = 4.dp)) {
                    Image(
                        painter = painterResource(id = R.drawable.close),
                        contentDescription = "Close",
                        modifier = Modifier
                            .size(40.dp)
                            .clickable { onBackPress.invoke() }
                    )
                }
            }
        }
    }
}