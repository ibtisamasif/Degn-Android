package com.example.degn.compose.wallet

import android.annotation.SuppressLint
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.degn.R
import com.example.degn.compose.BottomNavigationBar
import com.example.degn.compose.utils.BottomSheet
import com.example.degn.compose.utils.TopBar
import com.example.degn.ui.theme.Purple
import com.example.degn.ui.theme.DegnAppTheme
import com.example.degn.viewModels.wallet.WalletViewModel
import org.koin.androidx.compose.koinViewModel

@SuppressLint("DefaultLocale")
@Composable
fun WalletScreen(
    viewModel: WalletViewModel = koinViewModel(),
    onItemSelected: (String) -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.getUserDetail()
        viewModel.fetchUserBalance()
    }
    var openSheet by remember { mutableStateOf(false) }
    var sheetName by remember { mutableStateOf("") }
    val userBalance by viewModel.userBalance.collectAsState()
    DegnAppTheme {
        Scaffold(
            topBar = {
                Box(
                    modifier = Modifier
                        .padding(top = 45.dp)
                ) {
                    TopBar {
                        if(it=="Search"){
                            sheetName = "Search"
                            openSheet = true
                        }
                        else onItemSelected.invoke(it)
                    }
                }
            },
            bottomBar = { BottomNavigationBar(3) { onItemSelected.invoke(it) } }
        ) { paddingValues ->
            val userBalance = viewModel.userBalance.collectAsState().value
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(top = paddingValues.calculateTopPadding())
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(8.dp))
                if(viewModel.profileImage.isNullOrEmpty()) {
                    Image(
                        painter = painterResource(id = R.drawable.edit_profile),
                        contentDescription = "Profile Picture",
                        modifier = Modifier
                            .size(95.dp)
                    )
                }else{
                    AsyncImage(
                        model = viewModel.profileImage,
                        contentDescription = "Profile Picture from URL",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(95.dp)
                            .clip(CircleShape)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                (if(viewModel.userName.isNullOrEmpty()) "@username" else viewModel.userName)?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.titleSmall.copy(fontSize = 16.sp)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Balance Section
                Text(
                    text = "Total balance",
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "${String.format("%.2f", userBalance?.totalBalance) } USD",
                    style = MaterialTheme.typography.titleLarge,
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
                            text = "$${String.format("%.2f", userBalance?.cashBalance) }",
                            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 14.sp),
                            modifier = Modifier.padding(start = 8.dp, top = 4.dp)
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
                            modifier = Modifier.padding(start = 8.dp, top = 4.dp)
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
                                    change = "▲ 200.56%"
                                ) {
                                    openSheet = true
                                    sheetName = "Gain"
                                }
                            }
                        }
                    }
                }
                if (openSheet) {
                    BottomSheet(screenName = sheetName,  onCloseBottomSheet = {
                        when(sheetName){
                            "Deposit" -> onItemSelected.invoke("Buy")
                            "Withdraw" -> onItemSelected.invoke("Sell")
                        }
                        openSheet = it
                    }, amount = {viewModel.setAmount(it)})
                }
            }
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
    Holding("Bonk", "1198.1 tokens", "$0.22"),
    Holding("Chill Doge", "1198.1 tokens", "$0.22"),
    Holding("Shiba", "12000 tokens", "$0.50"),
    Holding("Bonk", "1198.1 tokens", "$0.22"),
    Holding("Chill Doge", "1198.1 tokens", "$0.22"),
    Holding("Shiba", "14000 tokens", "$0.50"),
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
                painter =  painterResource(id = if(name=="Bonk") R.drawable.bonk else if(name == "Chill Doge") R.drawable.chill_doge else R.drawable.dino),
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
                    style = MaterialTheme.typography.titleSmall.copy(fontSize = 16.sp),
                    modifier = Modifier.padding(bottom = 4.dp, start = 8.dp)
                )
                Text(
                    text = change,
                    color = Color.Green,
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
                        .padding(top = 4.dp)
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
