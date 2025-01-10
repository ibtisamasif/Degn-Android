package com.example.tradingapp.compose.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.tradingapp.R
import com.example.tradingapp.compose.utils.Title
import com.example.tradingapp.ui.theme.OffWhite

@Composable
fun TransactionDetailUI(
    onBackPress: () -> Unit
) {
    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .padding(top = 45.dp)
            ) { Title(title = "Bought Sani") { onBackPress.invoke() } }
        },
    ) { paddingValues ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(16.dp)
                .padding(paddingValues)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            AsyncImage(
                model = "https://raw.githubusercontent.com/solana-labs/token-list/main/assets/mainnet/Es9vMFrzaCERmJfrF4H2FYD4KCoNkY11McCe8BenwNYB/logo.svg",
                contentDescription = "Profile Picture from URL",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(80.dp)
                    .background(OffWhite, CircleShape)
                    .clip(CircleShape)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Amount
            Text(
                text = "-$3.50",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            DetailRow(label = "Status", value = "Complete")
            DetailRow(label = "Entry price", value = "$0.0402")
            DetailRow(label = "Fee", value = "$0.00")
            DetailRow(label = "Network fee", value = "$0.405")
            DetailRow(label = "Time", value = "11:03 PM - Dec 29, 2024")
            DetailRow(label = "ID", value = "3i2q...zDjS")
            DetailRow(label = "Need help?", value = "Support center")

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun DetailRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row {
            Icon(painter = painterResource(id = R.drawable.support), contentDescription = null , modifier = Modifier.size(18.dp))
            Text(
                text = label,
                fontSize = 16.sp,
                color = Color.Black,
                modifier = Modifier.padding(start = 12.dp)
            )
        }
        Text(
            text = value,
            fontSize = 16.sp,
            color = Color.Black,
            textAlign = TextAlign.End
        )
    }
}

@Preview
@Composable
fun DetailPreview(){
    TransactionDetailUI(onBackPress = {})
}