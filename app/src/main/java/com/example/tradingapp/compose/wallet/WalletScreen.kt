package com.example.tradingapp.compose.wallet

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tradingapp.R
import com.example.tradingapp.compose.BottomNavigationBar
import com.example.tradingapp.compose.utils.BottomSheet
import com.example.tradingapp.compose.utils.TopBar
import com.example.tradingapp.ui.theme.Purple
import com.example.tradingapp.ui.theme.TradingAppTheme

@Composable
fun WalletScreen(onItemSelected: (String) -> Unit) {
    var isShowSheet by remember { mutableStateOf(false) }
    TradingAppTheme {
        Scaffold(
            topBar = {
                Box(
                    modifier = Modifier
                        .padding(top = 45.dp)
                ) {
                    TopBar {
                        if(it=="Search") isShowSheet = true
                        else onItemSelected.invoke(it)
                    }
                }
            },
            bottomBar = { BottomNavigationBar(3) { onItemSelected.invoke(it) } }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(top = paddingValues.calculateTopPadding())
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                var openSheet by remember { mutableStateOf(false) }
                var sheetName by remember { mutableStateOf("") }
                // Profile Section
                Spacer(modifier = Modifier.height(8.dp))
                Image(
                    painter = painterResource(id = R.drawable.edit_profile), // Replace with actual drawable
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .size(95.dp)
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "@yourusername123",
                    style = MaterialTheme.typography.titleSmall.copy(fontSize = 16.sp)
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Balance Section
                Text(
                    text = "Total balance",
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "0.00 USD",
                    style = MaterialTheme.typography.titleLarge
                )

                Spacer(modifier = Modifier.height(4.dp))
                Row {
                    Text(
                        text = "▲ $0.226",
                        style = MaterialTheme.typography.displayLarge.copy(fontSize = 14.sp),
                        color = Color.Green
                    )
                    Text(
                        text = "Past day",
                        style = MaterialTheme.typography.bodyLarge.copy(fontSize = 14.sp),
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Action Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    ActionButton(icon = R.drawable.dollar_icon, label = "Add cash") {
                        openSheet = true
                        sheetName = "Deposit"
                    }
                    ActionButton(icon = R.drawable.send, label = "Send") {
                        openSheet = true
                        sheetName = "Send"
                    }
                    ActionButton(icon = R.drawable.ic_cashout, label = "Cash out") {
                        openSheet = true
                        sheetName = "Withdraw"
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Cash Section
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, Color.Black, CircleShape)
                        .padding(12.dp),
                ) {
                    Row(modifier = Modifier.weight(0.5f)) {
                        Text(
                            text = "Cash*",
                            style = MaterialTheme.typography.titleSmall.copy(fontSize = 18.sp)
                        )
                        Text(
                            text = "$0.00",
                            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 14.sp),
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                    Row(
                        modifier = Modifier.weight(0.5f),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_plus),
                            contentDescription = "Add Cash",
                            modifier = Modifier
                                .size(25.dp)
                                .clip(CircleShape)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Spacer(modifier = Modifier.height(16.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, Color.Black, RoundedCornerShape(21.dp))
                        .padding(top = 12.dp)
                        .padding(horizontal = 12.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    Row {
                        Text(
                            text = "Holdings",
                            style = MaterialTheme.typography.titleSmall.copy(fontSize = 18.sp)
                        )
                        Text(
                            text = "$0.00",
                            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 14.sp),
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(bottom = paddingValues.calculateBottomPadding())
                    ) {
                        LazyColumn(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            items(holdingList) { holding ->
                                HoldingItem(
                                    name = holding.name,
                                    amount = holding.tokens,
                                    value = holding.value,
                                    change = "200.56%"
                                ) {
                                    openSheet = true
                                    sheetName = "Gain"
                                }
                            }
                        }
                    }
                }
                if (openSheet) {
                    BottomSheet(sheetName) {
                        openSheet = it
                    }
                }
            }
        }
        if (isShowSheet) BottomSheet("Search"){
            isShowSheet = it
        }
    }
}

// Sample Data Class for Holdings
data class Holding(
    val name: String,
    val tokens: String,
    val value: String,
)

// Sample Holding List
val holdingList = listOf(
    Holding("Bonk", "1/981 tokens", "$0.22"),
    Holding("Bonk", "1/981 tokens", "$0.22"),
    Holding("Chill Doge", "1/981 tokens", "$0.22"),
    Holding("Chill Doge", "1/981 tokens", "$0.22"),
    Holding("Shiba", "1/1000 tokens", "$0.50"),
    Holding("Shiba", "1/1000 tokens", "$0.50"),
)


@Composable
fun ActionButton(icon: Int, label: String, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(16.dp)
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = label,
            modifier = Modifier
                .size(43.5.dp)
                .clip(CircleShape)
                .background(Purple)
                .padding(8.dp)
                .clickable { onClick.invoke() },
            tint = Color.White
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = label, style = MaterialTheme.typography.titleSmall)
    }
}

@Composable
fun HoldingItem(name: String, amount: String, value: String, change: String, onShare: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = R.drawable.bonk),
                contentDescription = name,
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(
                    text = name,
                    style = MaterialTheme.typography.titleSmall.copy(fontSize = 16.sp)
                )
                Text(
                    text = amount,
                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 12.sp)
                )
            }
        }
        Row(horizontalArrangement = Arrangement.End) {
            Column {
                Text(
                    text = value,
                    style = MaterialTheme.typography.titleSmall.copy(fontSize = 16.sp)
                )
                Text(
                    text = change,
                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 12.sp)
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.share),
                    contentDescription = "share",
                    modifier = Modifier
                        .size(25.dp)
                        .clip(CircleShape)
                        .background(Purple)
                        .padding(5.dp)
                        .clickable { onShare.invoke() }
                )
            }
        }
    }
}
