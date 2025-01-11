package com.example.degn.compose.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Settings
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
import com.example.degn.R
import com.example.degn.ui.theme.DegnAppTheme

@Composable
fun Title(title: String, isBottomSheet: Boolean = false, onBackPress: (String) -> Unit) {
    DegnAppTheme {
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Box(modifier = Modifier.align(Alignment.CenterStart)) {
                if (isBottomSheet) {
                    IconButton(onClick = { onBackPress.invoke("Slippage") }) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "Back",
                            modifier = Modifier.size(64.dp)
                        )
                    }
                } else {
                    IconButton(onClick = { onBackPress.invoke("Back") }) {
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
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(start = 12.dp, end = 4.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.close),
                        contentDescription = "Close",
                        modifier = Modifier
                            .size(40.dp)
                            .clickable { onBackPress.invoke("Back") }
                    )
                }
            }
        }
    }
}