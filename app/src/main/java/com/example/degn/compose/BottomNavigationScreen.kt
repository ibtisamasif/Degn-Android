package com.example.degn.compose

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.degn.R
import com.example.degn.data.Screens
import com.example.degn.ui.theme.Purple

@Composable
fun BottomNavigationBar(index: Int, onItemSelected: (String) -> Unit) {
    val selectedIndex = remember { mutableIntStateOf(index) }

    NavigationBar(
        containerColor = Color.White
    ) {
        NavigationBarItem(
            icon = {
                Icon(
                    painter = painterResource(id = if (selectedIndex.intValue == 0) R.drawable.home else R.drawable.home_outline),
                    contentDescription = "Home Icon",
                    Modifier.size(24.dp),
                    tint = if (selectedIndex.intValue == 0) Purple else Color.Black
                )
            },
            label = { Text("Home", style = MaterialTheme.typography.labelSmall) },
            selected = selectedIndex.intValue == 0,
            onClick = {
                selectedIndex.intValue = 0
                onItemSelected.invoke(Screens.HomeScreen.route)
            },
        )
        NavigationBarItem(
            icon = {
                Icon(
                    painter = painterResource(id = if (selectedIndex.intValue == 1) R.drawable.trending else R.drawable.trending_outline),
                    contentDescription = "Trending Icon",
                    Modifier.size(24.dp),
                    tint = if (selectedIndex.intValue == 1) Purple else Color.Black
                )
            },
            label = { Text("Trending", style = MaterialTheme.typography.labelSmall) },
            selected = selectedIndex.intValue == 1,
            onClick = {
                selectedIndex.intValue = 1
                onItemSelected.invoke(Screens.TrendingScreen.route)
            },
        )
        NavigationBarItem(
            icon = {
                Icon(
                    painter = painterResource(id = if (selectedIndex.intValue == 2) R.drawable.rewards else R.drawable.rewards_outline),
                    contentDescription = "Rewards Icon",
                    Modifier.size(24.dp),
                    tint = if (selectedIndex.intValue == 2) Purple else Color.Black
                )
            },
            label = { Text("Rewards", style = MaterialTheme.typography.labelSmall) },
            selected = selectedIndex.intValue == 2,
            onClick = {
                selectedIndex.intValue = 2
                onItemSelected.invoke(Screens.RewardScreen.route)
            },
        )
        NavigationBarItem(
            icon = {
                Icon(
                    painter = painterResource(id = if (selectedIndex.intValue == 3) R.drawable.wallet else R.drawable.wallet_outline),
                    contentDescription = "Wallet Icon",
                    Modifier.size(24.dp),
                    tint = if (selectedIndex.intValue == 3) Purple else Color.Black
                )
            },
            label = { Text("Wallet", style = MaterialTheme.typography.labelSmall) },
            selected = selectedIndex.intValue == 3,
            onClick = {
                selectedIndex.intValue = 3
                onItemSelected.invoke(Screens.WalletScreen.route)
            },
        )
    }
}