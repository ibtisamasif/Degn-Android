package com.example.tradingapp.compose.notification

import android.credentials.CredentialDescription
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import com.example.tradingapp.R
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tradingapp.compose.utils.CustomizedButton
import com.example.tradingapp.compose.utils.Title

@Composable
fun NotificationScreen(){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp)
    ) {
        Title("Notifications") {}
        Spacer(modifier = Modifier.height(16.dp))
        TitleAndDescription("Push Notification","Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed tristique vehicula purus.")
        Spacer(modifier = Modifier.height(16.dp))

        CustomizedButton("Enable in Setting",R.drawable.bell_icon){}
    }
}

@Composable
fun TitleAndDescription(title: String, description: String){
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge
        )
        Text(
            text = description,
            style = MaterialTheme.typography.bodySmall
        )
    }
}