package com.example.tradingapp.compose.home

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tradingapp.R
import com.example.tradingapp.compose.account.GainsScreen
import com.example.tradingapp.compose.support.SupportScreen
import com.example.tradingapp.ui.theme.Green
import com.example.tradingapp.ui.theme.Grey
import com.example.tradingapp.ui.theme.Purple
import kotlin.math.roundToInt

@Composable
fun CoinDetailScreen() {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .background(Color.White)

    ) {
        CoinHeader()

        // Graph Section (Placeholder for Graph)
        GraphSection()

        Spacer(modifier = Modifier.height(16.dp))

        // Balance Section
        BalanceDetail()

        Spacer(modifier = Modifier.height(16.dp))

        // About Section
        AboutSection()

        Spacer(modifier = Modifier.height(16.dp))

        // Coin Data Section (Market cap, volume, etc.)
        CoinDataSection()

        Spacer(modifier = Modifier.height(32.dp))

        // Buy Button
        BuyButton()
    }
}

@Composable
fun CoinHeader() {
    Column (
        modifier = Modifier.fillMaxWidth().padding(32.dp),
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Image(
                painter = painterResource(id = R.drawable.chill_doge), // replace with actual logo resource
                contentDescription = "Coin Logo",
                modifier = Modifier.size(40.dp).clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("Chill Doge", fontWeight = FontWeight.Bold, fontSize = 18.sp)

        }
        Spacer(modifier = Modifier.height(4.dp))
        Column(modifier = Modifier.fillMaxWidth()){
            Text("$0.225648",fontWeight = FontWeight.Bold, fontSize = 24.sp)
            Row{
                Text("$0.29% (200.34%)", fontSize = 14.sp, color = Green)
                Spacer(modifier = Modifier.width(2.dp))
                Text("Past day", fontSize = 14.sp)
            }
        }
    }
}

@Composable
fun GraphSection() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .background(Color.Gray.copy(alpha = 0.1f))
            .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
    ) {
        // Placeholder for Graph, replace with actual graph logic
        Text("Graph Placeholder", modifier = Modifier.align(Alignment.Center))
    }
}

@Composable
fun BalanceDetail() {
    Card(
        modifier = Modifier.fillMaxWidth().background(Color.White),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(modifier = Modifier.background(Color.White).padding(16.dp)) {
            Text("Your balance", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column {
                    Text("Value")
                    Text("0.00 USD", fontWeight = FontWeight.Bold)
                }
                Column(horizontalAlignment = Alignment.End) {
                    Text("Quantity")
                    Text("0")
                }
            }
        }
    }
}

@Composable
fun AboutSection() {
    Card(
        modifier = Modifier.fillMaxWidth().background(Color.White),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(modifier = Modifier.background(Color.White).padding(16.dp)) {
            Text("About", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus ac felis felis. Nullam ac quam sollicitudin.",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Composable
fun CoinDataSection() {
    Card(
        modifier = Modifier.fillMaxWidth().background(Color.White),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(modifier = Modifier.background(Color.White).padding(16.dp)) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Market cap", fontWeight = FontWeight.Bold)
                Text("$219M")
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Volume", fontWeight = FontWeight.Bold)
                Text("$5219M")
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Holders", fontWeight = FontWeight.Bold)
                Text("100k")
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Circulating supply", fontWeight = FontWeight.Bold)
                Text("999M")
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Created", fontWeight = FontWeight.Bold)
                Text("3d 12h ago")
            }
        }
    }
}

@Composable
fun BuyButton() {
    Button(
        onClick = { /* Handle Buy Action */ },
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .padding(bottom = 32.dp)
            .fillMaxWidth()
            .height(48.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Green),
        shape = RoundedCornerShape(12.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.dollar_icon),
            contentDescription = null,
            modifier = Modifier.padding(end = 8.dp),
            tint = Color.Black
        )
        Text("Buy", fontSize = 18.sp, color = Color.Black)
    }
}
