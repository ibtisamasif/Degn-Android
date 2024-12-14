package com.example.tradingapp.compose.rewards

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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.tradingapp.R
import com.example.tradingapp.compose.home.BottomNavigationBar
import com.example.tradingapp.compose.home.TopBar
import com.example.tradingapp.compose.utils.CustomizedButton
import com.example.tradingapp.ui.theme.TradingAppTheme

@Composable
fun RewardScreen(onItemSelected: (Int) -> Unit) {
    TradingAppTheme {
        Scaffold(
            topBar = {
                Box(
                    modifier = Modifier
                        .padding(top = 32.dp)
                ) {
                    TopBar{onItemSelected.invoke(it)}
                }
            },
            bottomBar = { BottomNavigationBar(2){onItemSelected.invoke(it)} }
        ) { paddingValues ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(24.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_dollar),
                    contentDescription = "Dollar Icon",
                    tint = Color(0xFF6C63FF),
                    modifier = Modifier
                        .size(90.dp)
                        .padding(bottom = 16.dp)
                )

                // Title
                Text(
                    text = "Make money when your friends trade",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(vertical = 8.dp),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Stats Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "Lifetime rewards",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            text = "$0.00",
                            style = MaterialTheme.typography.titleLarge,
                        )
                    }

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "Friends referred",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            text = "0",
                            style = MaterialTheme.typography.titleLarge,
                        )
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                CustomizedButton("Invite",16,R.drawable.share) { }

                Text(
                    text = "*Earn 50% of all trading fees from each friend you refer.",
                    style = MaterialTheme.typography.bodySmall,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
        }
    }
}

