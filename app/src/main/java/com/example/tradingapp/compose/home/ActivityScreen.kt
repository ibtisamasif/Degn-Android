package com.example.tradingapp.compose.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tradingapp.compose.BottomNavigationBar
import com.example.tradingapp.compose.utils.Title

@Composable
fun ActivityScreen(onBackPress: () -> Unit) {
    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .padding(top = 45.dp)
            ) { Title(title = "Activity") { onBackPress.invoke() } }
        },
        bottomBar = { BottomNavigationBar(0) {} }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            ListSection(2) {}
        }
    }
}