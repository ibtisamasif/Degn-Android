package com.example.tradingapp.compose.notification

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tradingapp.R
import com.example.tradingapp.compose.utils.CustomizedButton
import com.example.tradingapp.compose.utils.Title

@Composable
fun NotificationScreen(onBackPress: () -> Unit) {
    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .padding(top = 45.dp)
            ) {
                Title("Notifications") { onBackPress.invoke() }
            }
        }
    ) { paddingValues ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .background(Color.White)
                .fillMaxSize()
                .padding(paddingValues)
                .padding(start = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(32.dp))
            TitleAndDescription(
                "Push Notification",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed tristique vehicula purus."
            )
            Spacer(modifier = Modifier.height(32.dp))

            CustomizedButton("Enable in Setting", 64, R.drawable.bell_icon) {}
        }
    }
}

@Composable
fun TitleAndDescription(title: String, description: String) {
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium.copy(fontSize = 20.sp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = description,
            style = MaterialTheme.typography.labelMedium
        )
    }
}