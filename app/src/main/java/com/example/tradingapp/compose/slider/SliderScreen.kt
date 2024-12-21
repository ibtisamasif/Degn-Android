package com.example.tradingapp.compose.slider

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.tradingapp.R
import com.example.tradingapp.compose.utils.CustomizedButton

@Composable
fun SliderScreen(onButtonCLicked: ()-> Unit){
    val pagerState =
        rememberPagerState(initialPage = 0, initialPageOffsetFraction = 0f, pageCount = { 3 })

    Column(
        modifier = Modifier
            .background(Color.White)
            .padding(horizontal = 22.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        HorizontalPager(
            state = pagerState, modifier = Modifier.weight(1f)
        ) { page ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 16.dp)
            ) {
                Column(
                    modifier = Modifier.weight(0.8f),
                    verticalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.logo),
                        contentDescription = "Logo",
                        modifier = Modifier.size(220.dp)
                    )
                }
                Column(
                    modifier = Modifier.weight(0.3f),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = when (page) {
                            0 -> "Welcome"
                            1 -> "Learn and Explore"
                            2 -> "Join Us Today"
                            else -> ""
                        },
                        style = MaterialTheme.typography.headlineLarge,
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = when (page) {
                            0 -> "Lorem ipsum dolor sit amet, consectetur adipiscing elit."
                            1 -> "Discover features and tools that make your life easier."
                            2 -> "Sign up now and be part of our growing community."
                            else -> ""
                        },
                        style = MaterialTheme.typography.labelLarge,
                        modifier = Modifier.padding(horizontal = 12.dp)
                    )
                }
            }
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.weight(0.3f)
        ) {
            Row(
                modifier = Modifier
                    .padding(vertical = 16.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                repeat(3) { index ->
                    Box(
                        modifier = Modifier
                            .size(if (pagerState.currentPage == index) 8.dp else 4.dp)
                            .background(
                                if (pagerState.currentPage == index) Color.Black else Color.Gray,
                                shape = CircleShape
                            )
                    )
                    if (index != 2) Spacer(modifier = Modifier.width(8.dp))
                }
            }

            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                CustomizedButton("Next", 0, null) {
                    onButtonCLicked.invoke()
                }
            }
        }
    }
}