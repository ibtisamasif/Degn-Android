package com.example.tradingapp.compose.setting

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tradingapp.R
import com.example.tradingapp.compose.BottomNavigationBar
import com.example.tradingapp.compose.utils.BottomSheet
import com.example.tradingapp.compose.utils.Title
import com.example.tradingapp.ui.theme.OffWhite
import com.example.tradingapp.ui.theme.Sky

@Composable
fun SettingsScreen(onMenuCLicked: (String) -> Unit) {
    var isShowSheet by remember { mutableStateOf(false) }
    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .padding(top = 45.dp)
            ) {  Title(title = "Setting") { onMenuCLicked.invoke("Back") } }
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFF5F5F5), RoundedCornerShape(8.dp))
                    .padding(16.dp)
                    .clickable { onMenuCLicked.invoke("Profile") }
            ) {
                Icon(
                    imageVector = Icons.Rounded.AccountCircle,
                    contentDescription = "Profile Icon",
                    modifier = Modifier
                        .size(48.dp)
                        .background(OffWhite, CircleShape),
                    tint = Sky
                )
                Column {
                    Text(text = "@yourusername123", fontWeight = FontWeight.Bold)
                    Text(
                        text = "youremailaddress@gmail.com",
                        color = Color.Gray,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    imageVector = Icons.Default.KeyboardArrowRight,
                    contentDescription = "Go to Profile"
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Settings Options
            val settingsItems = listOf(
                Pair(R.drawable.notification, "Notifications"),
                Pair(R.drawable.lock, "Export Keys"),
                Pair(R.drawable.star, "Rate us"),
                Pair(R.drawable.legal, "Legal & Privacy"),
                Pair(R.drawable.support, "Support"),
                Pair(R.drawable.logout, "Log out")
            )

            settingsItems.forEach { item ->
                SettingItem(icon = item.first, title = item.second) { item ->
                    if (item == "Support") isShowSheet = true
                    else onMenuCLicked.invoke(item)
                }
            }

            Spacer(modifier = Modifier.height(64.dp))

            // Footer with version and icons
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_world),
                        contentDescription = "Website",
                        modifier = Modifier.size(32.dp)
                    )
                    Image(
                        painter = painterResource(id = R.drawable.ic_send),
                        contentDescription = "Contact Us", modifier = Modifier.size(32.dp)
                    )
                    Image(
                        painter = painterResource(id = R.drawable.ic_twitter),
                        contentDescription = "Close",
                        modifier = Modifier.size(32.dp)
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "v1.5", color = Color.Gray, fontSize = 12.sp)
            }
            if (isShowSheet) BottomSheet("Support") {
                isShowSheet = it
            }
        }
    }
}

@Composable
fun SettingItem(
    icon: Int,
    title: String,
    color: Color = Color.Black,
    onMenuCLicked: (String) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable { onMenuCLicked.invoke(title) }
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = title,
            modifier = Modifier.size(24.dp),
            tint = color
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = title, fontWeight = FontWeight.Medium, color = color)
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            imageVector = Icons.Default.KeyboardArrowRight,
            contentDescription = "Go to $title"
        )
    }
}
