package com.example.tradingapp.compose.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Card
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
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tradingapp.R
import com.example.tradingapp.compose.BottomNavigationBar
import com.example.tradingapp.compose.utils.BottomSheet
import com.example.tradingapp.compose.utils.TopBar
import com.example.tradingapp.data.Screens
import com.example.tradingapp.ui.theme.Green
import com.example.tradingapp.ui.theme.Red
import com.example.tradingapp.ui.theme.TradingAppTheme
import com.example.tradingapp.ui.theme.gradient

@Composable
fun HomeScreen(isHome: Boolean, onItemSelected: (String) -> Unit) {
    var isShowSheet by remember { mutableStateOf(false) }
    TradingAppTheme {
        Scaffold(
            topBar = {
                Box(
                    modifier = Modifier
                        .padding(top = 45.dp)
                ) {
                    TopBar {
                        if (it == "Search") isShowSheet = true
                        else onItemSelected.invoke(it)
                    }
                }
            },
            bottomBar = {
                BottomNavigationBar(if (isHome) 0 else 1) { onItemSelected.invoke(it) }
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = paddingValues.calculateTopPadding())
                    .padding(horizontal = 16.dp)
            ) {
                if (isHome) {
                    BalanceSection()
                }
                Spacer(modifier = Modifier.height(if (isHome) 16.dp else 12.dp))
                SpotlightSection()
                Spacer(modifier = Modifier.height(16.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = paddingValues.calculateBottomPadding())
                ) {
                    ListSection(10) {
                        onItemSelected.invoke(Screens.CoinDetailScreen.route)
                    }
                }
            }
        }
        if (isShowSheet) BottomSheet("Search") {
            isShowSheet = it
        }
    }

}

@Composable
fun BalanceSection() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
    ) {
        Text("Total balance", style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(8.dp))
        Text("0.00 USD", style = MaterialTheme.typography.titleLarge)
    }
}

@Composable
fun SpotlightSection() {
    Card(
        shape = RoundedCornerShape(21.5.dp),
        border = BorderStroke(1.dp, gradient),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .background(Color.White)
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .graphicsLayer(alpha = 0.99f)
                        .drawWithCache {
                            onDrawWithContent {
                                drawContent()
                                drawRect(gradient, blendMode = BlendMode.SrcIn)
                            }
                        }
                ) {
                    Icon(
                        painter = painterResource(R.drawable.star),
                        contentDescription = "Spotlight Icon",
                        modifier = Modifier.size(23.dp),
                        tint = Color.Unspecified
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text("Spotlight", style = MaterialTheme.typography.headlineSmall.copy(gradient))
                Spacer(modifier = Modifier.weight(1f))
                Box(
                    modifier = Modifier
                        .size(6.dp)
                        .background(
                            Green,
                            shape = CircleShape
                        )
                        .align(Alignment.CenterVertically)
                )
                Text(
                    "Live",
                    color = Green,
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.bonk),
                    contentDescription = "Bonk Icon",
                    modifier = Modifier
                        .size(57.dp)
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text("Bonk", style = MaterialTheme.typography.titleSmall.copy(fontSize = 18.sp))
                    Row {
                        Text(
                            "▲ $0.226",
                            style = MaterialTheme.typography.displayMedium,
                            color = Green
                        )
                        Text(
                            "Past day",
                            style = MaterialTheme.typography.labelSmall.copy(fontSize = 12.sp),
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.weight(1f))
                Text("$0.22", style = MaterialTheme.typography.titleSmall.copy(fontSize = 18.sp))
            }
        }
    }
}

@Composable
fun ListSection(count: Int, onItemSelected: () -> Unit) {
    LazyColumn {
        items(count) { index ->
            ListItem(
                image = if (index % 3 == 0) R.drawable.chill_doge else if (index % 3 == 1) R.drawable.dino else R.drawable.bonk,
                name = if (index % 3 == 0) "Chill Doge" else if (index % 3 == 1) "Dino" else "Bonk",
                price = "$0.22",
                marketCap = "$219M MKT CAP",
                isPositive = index % 2 == 0,
                onItemSelected = onItemSelected
            )
        }
    }
}

@Composable
fun ListItem(
    image: Int,
    name: String,
    price: String,
    marketCap: String,
    isPositive: Boolean,
    onItemSelected: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onItemSelected.invoke() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = image),
            contentDescription = "$name Icon",
            modifier = Modifier
                .size(44.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(name, style = MaterialTheme.typography.titleSmall.copy(fontSize = 18.sp))
            Text(marketCap, style = MaterialTheme.typography.labelMedium)
        }
        Spacer(modifier = Modifier.weight(1f))
        Column(horizontalAlignment = Alignment.End) {
            Text(price, style = MaterialTheme.typography.titleSmall.copy(fontSize = 18.sp))
            Text(
                text = if (isPositive) "▲ 200.56%" else "▼ 200.56%",
                color = if (isPositive) Green else Red,
                style = MaterialTheme.typography.displayMedium
            )
        }
    }
}



