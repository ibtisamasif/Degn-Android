package com.example.tradingapp.compose.home

import android.view.ViewGroup
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
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.tradingapp.R
import com.example.tradingapp.compose.BottomNavigationBar
import com.example.tradingapp.compose.account.GainsScreen
import com.example.tradingapp.compose.support.SupportScreen
import com.example.tradingapp.compose.utils.Title
import com.example.tradingapp.ui.theme.Green
import com.example.tradingapp.ui.theme.Grey
import com.example.tradingapp.ui.theme.Purple
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import kotlin.math.roundToInt

@Composable
fun CoinDetailScreen(onBackPress: () -> Unit) {
    val scrollState = rememberScrollState()
    Scaffold(
        topBar = { Box(
            modifier = Modifier
                .padding(top = 32.dp)
        ){ Title(title = "") { onBackPress.invoke() } } },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(scrollState)
                .background(Color.White)

        ) {
            CoinHeader()

            // Graph Section (Placeholder for Graph)
            GraphSection()

            Spacer(modifier = Modifier.height(16.dp))

            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                BalanceDetail()

                Spacer(modifier = Modifier.height(16.dp))

                AboutSection()

                Spacer(modifier = Modifier.height(16.dp))

                CoinDataSection()

                Spacer(modifier = Modifier.height(32.dp))

                BuyButton()
            }
        }
    }
}

    @Composable
    fun CoinHeader() {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp),
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Image(
                    painter = painterResource(id = R.drawable.chill_doge), // replace with actual logo resource
                    contentDescription = "Coin Logo",
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Chill Doge", fontWeight = FontWeight.Bold, fontSize = 18.sp)

            }
            Spacer(modifier = Modifier.height(4.dp))
            Column(modifier = Modifier.fillMaxWidth()) {
                Text("$0.225648", fontWeight = FontWeight.Bold, fontSize = 24.sp)
                Row {
                    Text("$0.29% (200.34%)", fontSize = 14.sp, color = Green)
                    Spacer(modifier = Modifier.width(2.dp))
                    Text("Past day", fontSize = 14.sp)
                }
            }
        }
    }

@Composable
fun GraphSection() {
    val chartData = remember { generateCryptoChartData() } // Generate dummy crypto data
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
fun BalanceDetail() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .border(1.dp, Color.Black, RoundedCornerShape(8.dp)), // Add border here
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier
                .background(Color.White)
                .padding(16.dp)
        ) {
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
            Text("About", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam rhoncus odio quis est vulputate blandit. Fusce posuere tellus lectus. Donec malesuada, dolor facilisis interdum convallis, nisi eros ultrices libero, at commodo lectus felis vel lacus. Aenean posuere, erat iaculis pulvinar ornare, velit justo dictum sem, a commodo ipsum elit ac mi..",
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
fun CoinDataSection() {
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
        onClick = {},
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
