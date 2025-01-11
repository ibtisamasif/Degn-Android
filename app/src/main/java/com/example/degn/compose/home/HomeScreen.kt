package com.example.degn.compose.home

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.degn.R
import com.example.degn.compose.BottomNavigationBar
import com.example.degn.compose.utils.BottomSheet
import com.example.degn.compose.utils.CircularProgress
import com.example.degn.compose.utils.TopBar
import com.example.degn.data.Screens
import com.example.degn.data.TokenDetails
import com.example.degn.ui.theme.Green
import com.example.degn.ui.theme.Red
import com.example.degn.ui.theme.DegnAppTheme
import com.example.degn.ui.theme.gradient
import com.example.degn.viewModels.home.HomeViewModel
import org.koin.androidx.compose.koinViewModel
import kotlin.math.abs

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = koinViewModel(),
    isHome: Boolean,
    onItemSelected: (String) -> Unit,
) {
    LaunchedEffect(Unit) {
        if (!viewModel.isTokensLoaded) {
            viewModel.fetchBalance()
            viewModel.fetchTokens()
            viewModel.fetchSpotlightTokens()
            viewModel.isTokensLoaded = true
        }
    }

    var isShowSheet by remember { mutableStateOf(false) }
    DegnAppTheme {
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
                    BalanceSection(viewModel = viewModel)
                }
                Spacer(modifier = Modifier.height(if (isHome) 16.dp else 12.dp))
                SpotlightSection(viewModel = viewModel, onItemSelected = {
                    onItemSelected.invoke(Screens.CoinDetailScreen.route+"/${it}")
                })
                Spacer(modifier = Modifier.height(16.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = paddingValues.calculateBottomPadding())
                ) {
                    if (isHome) {
                        viewModel.tokens.let {
                            it.value?.let { it1 ->
                                ListSection(
                                    list = it1,
                                    viewModel = viewModel,
                                    isHome = true,
                                    onItemSelected = { id->
                                        onItemSelected.invoke(Screens.CoinDetailScreen.route+"/${id}")
                                    },
                                    onLoadMore = {
                                        viewModel.offset.intValue += 10
                                        viewModel.fetchTokens()
                                    }
                                )
                            }
                        }
                    } else {
                        viewModel.spotlightTokens.let {
                            it.value?.let { it1 ->
                                ListSection(
                                    list = it1,
                                    viewModel = viewModel,
                                    isHome = false,
                                    onItemSelected = {
                                        onItemSelected.invoke(Screens.CoinDetailScreen.route)
                                    },
                                    onLoadMore = {
                                        viewModel.offset.intValue += 10
                                        viewModel.fetchMoreSpotlightTokens()
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
        if (viewModel.isLoading.value) {
            CircularProgress()
        }
        if (isShowSheet) BottomSheet(screenName = "Search", onCloseBottomSheet = {
            isShowSheet = it
        }, amount = {})
    }

}

@SuppressLint("DefaultLocale")
@Composable
fun BalanceSection(viewModel: HomeViewModel) {
    val balance = viewModel.userBalance.collectAsState().value
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
    ) {
        Text("Total balance", style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = if (balance?.totalBalance != null) "${
                balance.totalBalance.let {
                    String.format(
                        "%.2f",
                        it
                    )
                }
            } USD" else "", style = MaterialTheme.typography.titleLarge
        )
    }
}

@Composable
fun SpotlightSection(viewModel: HomeViewModel, onItemSelected: (String) -> Unit) {
    val tokens = viewModel.spotlightTokens.collectAsState().value
    if (tokens?.isNotEmpty() == true) {
        val firstToken = tokens[0]

        Card(
            shape = RoundedCornerShape(21.5.dp),
            border = BorderStroke(1.dp, gradient),
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    viewModel.selectedCoinId.value = firstToken._id
                    onItemSelected.invoke(firstToken._id)
                }
        ) {
            Column(
                modifier = Modifier
                    .background(Color.White)
                    .padding(16.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
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
                    Text(
                        text = "Spotlight",
                        style = MaterialTheme.typography.headlineSmall.copy(gradient)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Box(
                        modifier = Modifier
                            .padding(top = 3.dp)
                            .size(7.dp)
                            .background(Green, shape = CircleShape)
                            .align(Alignment.CenterVertically)
                    )
                    Text(
                        text = "Live",
                        color = Green,
                        style = MaterialTheme.typography.titleSmall,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    AsyncImage(
                        model = firstToken.icon,
                        contentDescription = "Spotlight",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(57.dp)
                            .clip(CircleShape)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 4.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = firstToken.name,
                                style = MaterialTheme.typography.titleSmall.copy(fontSize = 18.sp)
                            )
                            Text(
                                text = "$" + "%.2f".format(firstToken.price.toDouble()),
                                style = MaterialTheme.typography.titleSmall.copy(fontSize = 18.sp)
                            )
                        }
                        Row {
                            Text(
                                text = "▲ $0.226",
                                style = MaterialTheme.typography.displayMedium,
                                color = Green
                            )
                            Text(
                                text = "Past day",
                                style = MaterialTheme.typography.labelSmall.copy(fontSize = 12.sp),
                                modifier = Modifier.padding(start = 8.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun ListSection(
    list: List<TokenDetails>,
    viewModel: HomeViewModel,
    isHome: Boolean,
    onItemSelected: (String) -> Unit,
    onLoadMore: () -> Unit
) {
    LazyColumn {
        items(list.size) { index ->
            ListItem(
                id = list[index]._id,
                image = list[index].icon,
                name = list[index].name,
                price = "$" + viewModel.formatDouble(list[index].price.toDouble()),
                marketCap = "$${viewModel.abbreviateNumber(list[index].marketCap.toLong())} MKT CAP",
                item = list[index],
                viewModel = viewModel,
                onItemSelected = onItemSelected
            )
            if (index == list.lastIndex && viewModel.newItemLoaded.value && isHome) {
                viewModel.newItemLoaded.value = false
                onLoadMore()
            } else if (index == list.lastIndex && viewModel.newSpotLightItemLoaded.value && !isHome) {
                viewModel.newSpotLightItemLoaded.value = false
                onLoadMore()
            }
        }
    }
}


@Composable
fun ListItem(
    id: String,
    image: String,
    name: String,
    price: String,
    item: TokenDetails,
    marketCap: String,
    viewModel: HomeViewModel,
    onItemSelected: (String) -> Unit
) {
    val change = item.priceChange24h.toDoubleOrNull() ?: 0.0
    val changeText = if (change > 0) {
        "▲ %.3f%%".format(change)
    } else {
        "▼ %.3f%%".format(abs(change))
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable {
                viewModel.selectedCoinId.value = id
                onItemSelected.invoke(id)
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = image,
            contentDescription = "$name Icon",
            contentScale = ContentScale.Crop,
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
                    text = changeText,
                    color = if (change > 0) Green else Red,
                    style = MaterialTheme.typography.displayMedium
                )
        }
    }
}



