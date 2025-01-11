package com.example.degn.compose.support

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.degn.R
import com.example.degn.compose.BottomNavigationBar
import com.example.degn.compose.setting.SettingItem
import com.example.degn.compose.utils.Title

@Composable
fun LegalAndPrivacy(onBackPress:(String)->Unit){
    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .padding(top = 45.dp)
            ) {  Title(title = "Legal & Privacy") { onBackPress.invoke("Back") } }
        },
        bottomBar = { BottomNavigationBar(0) {} }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            val settingsItems = listOf(
                Pair(R.drawable.legal, "Term of Service"),
                Pair(R.drawable.lock, "Privacy Policy"),
                Pair(R.drawable.delete, "Delete Account"),
            )

            settingsItems.forEach { item ->
                val color = if (item.second == "Delete Account") Color.Red else Color.Black
                SettingItem(icon = item.first, title = item.second, color = color) {}
            }
        }
    }
}