package com.example.degn.compose.utils

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.degn.R
import com.example.degn.data.Screens

@Composable
fun TopBar(onSettingClicked: (String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.height(64.dp))
        Icon(
            painter = painterResource(R.drawable.history),
            contentDescription = "History Icon",
            modifier = Modifier
                .padding(start = 16.dp)
                .size(20.dp)
                .clickable { onSettingClicked.invoke(Screens.ActivityScreen.route) }
        )

        SearchField(
            onClick = {onSettingClicked.invoke("Search")}
        )

        Icon(
            Icons.Default.Settings,
            contentDescription = "Settings Icon",
            modifier = Modifier
                .padding(end = 16.dp)
                .clickable { onSettingClicked.invoke(Screens.SettingScreen.route) }
        )
    }
}