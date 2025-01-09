package com.example.tradingapp.compose.home

import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import coil.compose.AsyncImage
import com.example.tradingapp.R
import com.example.tradingapp.compose.utils.BottomSheet
import com.example.tradingapp.compose.utils.CircularProgress
import com.example.tradingapp.compose.utils.Title
import com.example.tradingapp.ui.theme.Green
import com.example.tradingapp.ui.theme.Red
import com.example.tradingapp.viewModels.home.HomeViewModel
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun CoinDetailScreen(
    viewModel: HomeViewModel = koinViewModel(),
    onBackPress: () -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.isLoading.value = true
        viewModel.fetchTokenByID(viewModel.selectedCoinId.value)
        viewModel.fetchUserTokenBalance(viewModel.selectedCoinId.value)
    }
    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberScrollState()
    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .padding(top = 32.dp)
            ) { Title(title = "") { onBackPress.invoke() } }
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(scrollState)
                .background(Color.White)

        ) {
            if (!viewModel.isLoading.value) {
                CoinHeader(viewModel = viewModel)
                GraphSection()

                Spacer(modifier = Modifier.height(16.dp))

                Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                    BalanceDetail(viewModel = viewModel)

                    Spacer(modifier = Modifier.height(16.dp))

                    AboutSection(viewModel = viewModel)

                    Spacer(modifier = Modifier.height(16.dp))

                    CoinDataSection(viewModel = viewModel)

                    Spacer(modifier = Modifier.height(32.dp))

                    val balance = viewModel.userTokenBalance.collectAsState()
                    val tokenBalance = viewModel.tokenBalance.collectAsState()

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    ) {
                        balance.value.let {
                            if (tokenBalance.value != null) {
                                BuySellButton(
                                    buttonText = "Buy",
                                    textColor = Color.Black,
                                    buttonColor = Green,
                                    modifier = Modifier.weight(1f),
                                    onClick = {
                                        viewModel.transaction.value = "Buy"
                                    }
                                )
                                Spacer(modifier = Modifier.width(16.dp))
                                BuySellButton(
                                    buttonText = "Sell",
                                    textColor = Color.White,
                                    buttonColor = Red,
                                    modifier = Modifier.weight(1f),
                                    onClick = {viewModel.transaction.value = "Sell"}
                                )
                            } else {
                                BuySellButton(
                                    buttonText = "Buy",
                                    textColor = Color.Black,
                                    buttonColor = Green,
                                    modifier = Modifier.fillMaxWidth(),
                                    onClick = {
                                        if(balance.value?.cashBalance!! > 0) {
                                            viewModel.transaction.value = "Buy"
                                        }
                                    }
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
    if(viewModel.isLoading.value){
        CircularProgress()
    }

    if(viewModel.transaction.value != ""){
        BottomSheet(screenName = viewModel.transaction.value, onCloseBottomSheet =  {
            viewModel.transaction.value = "" }, amount = {amount ->
            coroutineScope.launch {
                if(viewModel.transaction.value == "Buy") viewModel.buyTransaction(amount)
                else viewModel.sellTransaction(amount)
            }
        })
    }

}

@Composable
fun CoinHeader(
    viewModel: HomeViewModel
) {
    val token by viewModel.token.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            AsyncImage(
                model = token?.image ?: "",
                contentDescription = "Spotlight",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(57.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = token?.name ?: "Loading...",
                style = MaterialTheme.typography.titleSmall.copy(fontSize = 18.sp),
                modifier = Modifier.padding(top = 20.dp)
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                "$" + "%.6f".format(token?.marketCap?.toDoubleOrNull() ?: 0.0),
                style = MaterialTheme.typography.titleLarge.copy(fontSize = 32.sp)
            )
            Row {
                Text(
                    "$0.29% (200.34%)",
                    style = MaterialTheme.typography.displayMedium,
                    color = Green
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text("Past day", style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}


@Composable
fun GraphSection() {
    val chartData = remember { generateCryptoChartData() }
    AndroidView(
        factory = { context ->
            LineChart(context).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                setupChart(this, chartData)
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .background(Color.Gray.copy(alpha = 0.1f))
            .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
    )
}

fun generateCryptoChartData(): LineData {
    val entries = mutableListOf<Entry>()
    for (i in 0..100) {
        entries.add(Entry(i.toFloat(), (Math.sin(i * 0.1) * 100).toFloat()))
    }

    val lineDataSet = LineDataSet(entries, "Crypto Prices").apply {
        color = Color.Blue.toArgb()
        lineWidth = 2f
        setDrawCircles(false)
        setDrawValues(false)
        mode = LineDataSet.Mode.CUBIC_BEZIER
    }

    return LineData(lineDataSet)
}

fun setupChart(chart: LineChart, lineData: LineData) {
    chart.apply {
        data = lineData
        description.isEnabled = false
        xAxis.apply {
            position = XAxis.XAxisPosition.BOTTOM
            setDrawGridLines(false)
            textColor = Color.Gray.toArgb()
        }
        axisLeft.apply {
            setDrawGridLines(false)
            textColor = Color.Gray.toArgb()
        }
        axisRight.isEnabled = false
        legend.isEnabled = false
        setTouchEnabled(true)
        isDragEnabled = true
        setScaleEnabled(true)
        setPinchZoom(true)
    }
}


@Composable
fun BalanceDetail(
    viewModel: HomeViewModel
) {
    val tokenBalance = viewModel.tokenBalance.collectAsState()
    val tokenQuantity = viewModel.tokenQuantity.collectAsState()
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .border(1.dp, Color.Black, RoundedCornerShape(8.dp)),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier
                .background(Color.White)
                .padding(16.dp)
        ) {
            Text("Your balance", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(end = 16.dp)
                ) {
                    Text("Value", style = MaterialTheme.typography.labelMedium)
                    Text(
                        text = if(tokenBalance.value != null) "${tokenBalance.value?.toDoubleOrNull()?.let {
                            String.format("%.4f", it)
                        }} USD" else "0.00 USD",
                        style = MaterialTheme.typography.titleMedium.copy(fontSize = 20.sp)
                    )
                }
                Column(
                    modifier = Modifier.padding(start = 16.dp)
                ) {
                    Text("Quantity", style = MaterialTheme.typography.labelMedium)
                    Text(text = if(tokenQuantity.value != null) "${tokenQuantity.value?.toDoubleOrNull()?.let {
                        String.format("%.4f", it)
                    }}" else "0", style = MaterialTheme.typography.titleMedium.copy(fontSize = 20.sp))
                }
            }
        }
    }
}

@Composable
fun AboutSection(
    viewModel: HomeViewModel
) {
    val token by viewModel.token.collectAsState()
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .border(1.dp, Color.Black, RoundedCornerShape(8.dp)),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier
                .background(Color.White)
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text("About", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = token?.description ?: "Loading description...",
                style = MaterialTheme.typography.bodySmall
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row {
                Image(
                    painter = painterResource(id = R.drawable.ic_world),
                    contentDescription = null,
                    modifier = Modifier.padding(horizontal = 4.dp)
                )
                Image(
                    painter = painterResource(id = R.drawable.ic_send),
                    contentDescription = null,
                    modifier = Modifier.padding(horizontal = 4.dp)
                )
                Image(
                    painter = painterResource(id = R.drawable.ic_twitter),
                    contentDescription = null,
                    modifier = Modifier.padding(horizontal = 4.dp)
                )
            }
        }
    }
}

@Composable
fun CoinDataSection(viewModel: HomeViewModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier
                .background(Color.White)
                .padding(16.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    "Market cap",
                    style = MaterialTheme.typography.titleMedium.copy(fontSize = 18.sp)
                )
                Text("$219M", style = MaterialTheme.typography.titleMedium.copy(fontSize = 18.sp))
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Volume", style = MaterialTheme.typography.titleMedium.copy(fontSize = 18.sp))
                Text("$5219M", style = MaterialTheme.typography.titleMedium.copy(fontSize = 18.sp))
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Holders", style = MaterialTheme.typography.titleMedium.copy(fontSize = 18.sp))
                Text("100k", style = MaterialTheme.typography.titleMedium.copy(fontSize = 18.sp))
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    "Circulating supply",
                    style = MaterialTheme.typography.titleMedium.copy(fontSize = 18.sp)
                )
                Text("999M", style = MaterialTheme.typography.titleMedium.copy(fontSize = 18.sp))
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Created", style = MaterialTheme.typography.titleMedium.copy(fontSize = 18.sp))
                Text(
                    viewModel.tokenCreationDate.value,
                    style = MaterialTheme.typography.titleMedium.copy(fontSize = 18.sp)
                )
            }
        }
    }
}

@Composable
fun BuySellButton(
    buttonText: String,
    textColor: Color,
    buttonColor: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Button(
        onClick = { onClick.invoke() },
        modifier = modifier
            .height(48.dp),
        colors = ButtonDefaults.buttonColors(containerColor = buttonColor),
        shape = RoundedCornerShape(12.dp)
    ) {
        Box(modifier = Modifier
            .size(24.dp)
            .clip(CircleShape)
            .background(textColor)) {
            Icon(
                painter = painterResource(id = R.drawable.dollar_icon),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize(0.7f)
                    .align(Alignment.Center),
                tint = buttonColor
            )
        }
        Text(buttonText, fontSize = 18.sp, color = textColor, modifier = Modifier.padding(start = 8.dp, bottom = 2.dp))
    }
}
