package com.example.tradingapp.compose.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tradingapp.R
import com.example.tradingapp.ui.theme.Green
import com.example.tradingapp.ui.theme.Red
import com.example.tradingapp.ui.theme.TradingAppTheme

@Composable
fun HomeScreen(isHome: Boolean) {
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
            bottomBar = { BottomNavigationBar(if (isHome) 0 else 1) }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                if (isHome) {
                    BalanceSection()
                }
                Spacer(modifier = Modifier.height(if (isHome) 16.dp else 12.dp))
                SpotlightSection()
                Spacer(modifier = Modifier.height(16.dp))
                ListSection()
            }
        }
    }
}


@Composable
fun TopBar() {
    var search by remember { mutableStateOf("") }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.height(64.dp))
        Icon(Icons.Default.Face, contentDescription = "History Icon")
        OutlinedTextField(
            value = search,
            onValueChange = { search = it },
            placeholder = {
                Text(
                    text = "Search",
                    style = MaterialTheme.typography.labelSmall
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search Icon"
                )
            },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth(.85f)
                .height(50.dp)
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(24.dp),
            colors = OutlinedTextFieldDefaults.colors(
                cursorColor = Color.Black,
                focusedBorderColor = MaterialTheme.colorScheme.outline,
                unfocusedBorderColor = MaterialTheme.colorScheme.outline,
            ),
        )
        Icon(Icons.Default.Settings, contentDescription = "Settings Icon")
    }
}

@Composable
fun BalanceSection() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text("Total balance", style = MaterialTheme.typography.labelLarge)
        Spacer(modifier = Modifier.height(8.dp))
        Text("0.00 USD", style = MaterialTheme.typography.displayMedium)
    }
}

@Composable
fun SpotlightSection() {
    Card(
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, Green),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Default.Star, contentDescription = "Spotlight Icon", tint = Green)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Spotlight", style = MaterialTheme.typography.titleMedium, color = Green)
                Spacer(modifier = Modifier.weight(1f))
                Text("Live", color = Green)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.bonk),
                    contentDescription = "Bonk Icon",
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text("Bonk", style = MaterialTheme.typography.bodyLarge)
                    Text(
                        "+$0.226 Past day",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Green
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                Text("$0.22", style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}

@Composable
fun ListSection() {
    LazyColumn {
        items(10) { index ->
            ListItem(
                image = if (index % 3 == 0) R.drawable.chill_doge else if (index % 3 == 1) R.drawable.dino else R.drawable.bonk,
                name = if (index % 3 == 0) "Chill Doge" else if (index % 3 == 1) "Dino" else "Bonk",
                price = "$0.22",
                marketCap = "$219M MKT CAP",
                isPositive = index % 2 == 0
            )
        }
    }
}

@Composable
fun ListItem(image: Int, name: String, price: String, marketCap: String, isPositive: Boolean) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = image),
            contentDescription = "$name Icon",
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(name, style = MaterialTheme.typography.titleLarge)
            Text(marketCap, style = MaterialTheme.typography.bodyMedium)
        }
        Spacer(modifier = Modifier.weight(1f))
        Column(horizontalAlignment = Alignment.End) {
            Text(price, style = MaterialTheme.typography.titleLarge)
            Text(
                text = if (isPositive) "+200.56%" else "-200.56%",
                color = if (isPositive) Green else Red,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Composable
fun BottomNavigationBar(index: Int) {
    val selectedIndex = remember { mutableIntStateOf(index) }

    NavigationBar(
        containerColor = if (isSystemInDarkTheme()) Color.Black else Color.White
    ) {
        NavigationBarItem(
            icon = {
                Image(
                    painter = painterResource(id = if (selectedIndex.intValue == 0) R.drawable.home else R.drawable.home_outline),
                    contentDescription = "Home Icon",
                    Modifier.size(24.dp)
                )
            },
            label = { Text("Home") },
            selected = selectedIndex.intValue == 0,
            onClick = { selectedIndex.intValue = 0 },
            colors = NavigationBarItemDefaults.colors(
                indicatorColor = Color.Transparent
            )
        )
        NavigationBarItem(
            icon = {
                Image(
                    painter = painterResource(id = if (selectedIndex.intValue == 1) R.drawable.trending else R.drawable.trending_outline),
                    contentDescription = "Trending Icon",
                    Modifier.size(24.dp)
                )
            },
            label = { Text("Trending") },
            selected = selectedIndex.intValue == 1,
            onClick = { selectedIndex.intValue = 1 },
            colors = NavigationBarItemDefaults.colors(
                indicatorColor = Color.Transparent // Remove the hover background effect
            )
        )
        NavigationBarItem(
            icon = {
                Image(
                    painter = painterResource(id = if (selectedIndex.intValue == 2) R.drawable.rewards else R.drawable.rewards_outline),
                    contentDescription = "Rewards Icon",
                    Modifier.size(24.dp)
                )
            },
            label = { Text("Rewards") },
            selected = selectedIndex.intValue == 2,
            onClick = { selectedIndex.intValue = 2 },
            colors = NavigationBarItemDefaults.colors(
                indicatorColor = Color.Transparent
            )
        )
        NavigationBarItem(
            icon = {
                Image(
                    painter = painterResource(id = if (selectedIndex.intValue == 3) R.drawable.wallet else R.drawable.wallet_outline),
                    contentDescription = "Wallet Icon",
                    Modifier.size(24.dp),
                )
            },
            label = { Text("Wallet") },
            selected = selectedIndex.intValue == 3,
            onClick = { selectedIndex.intValue = 3 },
            colors = NavigationBarItemDefaults.colors(
                indicatorColor = Color.Transparent
            )
        )
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewHomeScreen() {
    HomeScreen(true)
}
