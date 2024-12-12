package com.example.tradingapp.compose.wallet

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.tradingapp.R
import com.example.tradingapp.compose.home.BottomNavigationBar
import com.example.tradingapp.compose.home.TopBar
import com.example.tradingapp.ui.theme.TradingAppTheme

@Composable
fun WalletScreen() {
    TradingAppTheme {
        Scaffold(
            topBar = {
                Box(
                    modifier = Modifier
                        .padding(top = 32.dp)
                ) {
                    TopBar()
                }
            },
            bottomBar = { BottomNavigationBar(3) }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                // Profile Icon
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFF0F0F0)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Profile Icon",
                        tint = Color.Gray,
                        modifier = Modifier.size(40.dp)
                    )
                }

                // Username
                Text(
                    text = "@yourusername123",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = 8.dp)
                )

                // Total Balance
                Text(
                    text = "Total balance",
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(top = 16.dp)
                )
                Text(
                    text = "0.00 USD",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(top = 8.dp)
                )
                Text(
                    text = "\u25B2 $0.226 Past day",
                    style = MaterialTheme.typography.bodySmall.copy(color = Color(0xFF00C853)),
                    modifier = Modifier.padding(top = 4.dp)
                )

                // Action Buttons
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 24.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    ActionButton(iconRes = R.drawable.dollar_icon, label = "Add cash")
                    ActionButton(iconRes = R.drawable.send, label = "Send")
                    ActionButton(iconRes = R.drawable.ic_cashout, label = "Cash out")
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Cash Section
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = "Cash*", style = MaterialTheme.typography.bodyMedium)
                            Text(text = "$0.00", style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Holdings Section
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Holdings $0.00",
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )

                        LazyColumn(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            items(holdingList) { holding ->
                                HoldingItem(
                                    name = holding.name,
                                    tokens = holding.tokens,
                                    value = holding.value,
                                    growthColor = holding.growthColor
                                )
                                Divider(modifier = Modifier.padding(vertical = 8.dp))
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ActionButton(iconRes: Int, label: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable { /* Handle click */ }
    ) {
        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = label,
            tint = Color(0xFF6C63FF),
            modifier = Modifier.size(40.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Composable
fun HoldingItem(name: String, tokens: String, value: String, growthColor: Color) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(text = name, style = MaterialTheme.typography.bodyMedium)
            Text(text = tokens, style = MaterialTheme.typography.bodySmall)
        }
        Column(horizontalAlignment = Alignment.End) {
            Text(text = value, style = MaterialTheme.typography.bodyMedium)
            Text(
                text = "\u25B2 200.55%",
                style = MaterialTheme.typography.bodySmall.copy(color = growthColor)
            )
        }
    }
}

// Sample Data Class for Holdings
data class Holding(
    val name: String,
    val tokens: String,
    val value: String,
    val growthColor: Color
)

// Sample Holding List
val holdingList = listOf(
    Holding("Bonk", "1/981 tokens", "$0.22", Color(0xFF00C853)),
    Holding("Chill Doge", "1/981 tokens", "$0.22", Color(0xFF00C853)),
    Holding("Shiba", "1/1000 tokens", "$0.50", Color(0xFF00C853))
)
