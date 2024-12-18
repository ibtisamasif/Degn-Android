package com.example.tradingapp.compose.export

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.example.tradingapp.R
import com.example.tradingapp.compose.notification.TitleAndDescription
import com.example.tradingapp.compose.utils.Title
import com.example.tradingapp.ui.theme.Grey

@Composable
fun ExportKeysScreen(onBackPress:()->Unit) {
    Scaffold(
        topBar = {
          Title("Export keys") { onBackPress.invoke()}
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            TitleAndDescription("Your Wallets","Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed tristique vehicula purus.")
            Spacer(modifier = Modifier.height(16.dp))
            WalletCard(name = "Solana", key = "Ab....kL")
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Advanced",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(8.dp))
            SecretPhraseCard()
        }
    }
}

@Composable
fun WalletCard(name: String, key: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .height(45.dp)
            .border(1.dp, Color.Black, RoundedCornerShape(18.dp))
            .background(Color.White)
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .clickable { },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.solana),
            contentDescription = null,
            tint = Color.Black,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = "$name:",
            fontSize = 16.sp,
            color = Color.Black,
            modifier = Modifier.weight(1f),
            overflow = TextOverflow.Clip
        )
        Text(
            text = key,
            fontSize = 12.sp,
            color = Grey,
            modifier = Modifier.weight(0.4f),
            overflow = TextOverflow.Ellipsis
        )
        IconButton(onClick = { }) {
            Icon(
                painter = painterResource(id = R.drawable.copy),
                contentDescription = null,
            )
        }
    }
}

@Composable
fun SecretPhraseCard() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .height(45.dp)
            .border(1.dp, Color.Black, RoundedCornerShape(18.dp))
            .background(Color.White)
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .clickable { },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.lock),
            contentDescription = null,
            tint = Color.Black,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = "Secret phrase",
            fontSize = 16.sp,
            color = Color.Black,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = "Export",
            fontSize = 16.sp,
            color = Grey
        )
        Spacer(modifier = Modifier.width(8.dp))
        Icon(Icons.Default.KeyboardArrowRight, contentDescription = null, modifier = Modifier.size(32.dp).padding(top = 2.dp), tint = Grey)
    }
}
