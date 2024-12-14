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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tradingapp.R
import com.example.tradingapp.compose.BottomNavigationBar
import com.example.tradingapp.compose.utils.CustomizedButton
import com.example.tradingapp.compose.utils.TopBar
import com.example.tradingapp.ui.theme.TradingAppTheme

@Composable
fun RewardScreen(onItemSelected: (Int) -> Unit) {
    TradingAppTheme {
        Scaffold(
            topBar = {
                Box(
                    modifier = Modifier
                        .padding(top = 45.dp)
                ) {
                    TopBar { onItemSelected.invoke(it) }
                }
            },
            bottomBar = { BottomNavigationBar(2) { onItemSelected.invoke(it) } }
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
                        .size(76.dp)
                )

                // Title
                Column(
                    modifier = Modifier.padding(vertical = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Make money when your",
                        style = MaterialTheme.typography.titleMedium.copy(fontSize = 20.sp)
                    )
                    Text(
                        text = "friends trade",
                        style = MaterialTheme.typography.titleMedium.copy(fontSize = 20.sp),
                    )
                }


                // Stats Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "Lifetime rewards",
                            style = MaterialTheme.typography.labelLarge
                        )
                        Text(
                            text = "$0.00",
                            style = MaterialTheme.typography.titleMedium.copy(fontSize = 20.sp),
                        )
                    }

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "Friends referred",
                            style = MaterialTheme.typography.labelLarge
                        )
                        Text(
                            text = "0",
                            style = MaterialTheme.typography.titleMedium.copy(fontSize = 20.sp),
                        )
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                CustomizedButton("Invite", 16, R.drawable.share) { }

                Text(
                    text = buildAnnotatedString {
                        append("*Earn ")
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("50%")
                        }
                        append(" of all trading fees from each friend you refer.")
                    },
                    style = MaterialTheme.typography.bodySmall,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

            }
        }
    }
}

