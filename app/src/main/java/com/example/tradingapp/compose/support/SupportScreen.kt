package com.example.tradingapp.compose.support

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.tradingapp.R

@Composable
fun SupportScreen(onCloseBottomSheet: (Boolean) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.97f)
            .background(Color.White, RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
            .padding(16.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.Start
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Absolute.Left
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.three_circles),
                        contentDescription = null,
                        modifier = Modifier.size(82.dp, 34.dp)
                    )
                }
                Image(
                    painter = painterResource(id = R.drawable.close),
                    contentDescription = "Close",
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(horizontal = 8.dp)
                        .size(36.dp)
                        .clickable { onCloseBottomSheet.invoke(true) }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Hi yourusername123",
                style = MaterialTheme.typography.titleMedium,
                color = Color.Gray,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = "How can we help?",
                style = MaterialTheme.typography.titleMedium,
            )

            Spacer(modifier = Modifier.height(24.dp))

            val options = listOf(
                Pair("Messages", R.drawable.message),
                Pair("Help", R.drawable.support),
                Pair("Send us a message", R.drawable.send_message),
                Pair("Search for help", R.drawable.search)
            )

            options.forEach { option ->
                OptionItem(title = option.first, icon = option.second)
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun OptionItem(title: String, icon: Int) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(42.dp)
            .background(Color.White, RoundedCornerShape(13.dp))
            .border(1.dp, Color.Gray, RoundedCornerShape(13.dp))
            .padding(12.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleSmall.copy(textAlign = TextAlign.Start),
            modifier = Modifier.weight(1f).padding(start = 8.dp)
        )
        Icon(
            painter = painterResource(id = icon),
            contentDescription = title,
            tint = Color.Black,
            modifier = Modifier.padding(end = 8.dp)
        )
    }
}
