package com.example.tradingapp.compose.account

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsEndWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tradingapp.R
import com.example.tradingapp.compose.utils.CustomizedButton
import com.example.tradingapp.ui.theme.Grey

@SuppressLint("InvalidColorHexValue")
@Composable
fun GainsScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Close button
        Image(
            painter = painterResource(id = R.drawable.close), // replace with your close button image resource
            contentDescription = "Close",
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp)
                .size(24.dp)
        )

        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
        ) {
            CustomizedButton("Share gains",R.drawable.share){}
        }

        // Central Card
        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .size(280.dp)
        ) {
            Box(
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(260.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Grey)
                    .alpha(0.45f)
                    .blur(radius = 1.dp)
                    .border(1.dp, Color.LightGray, RoundedCornerShape(16.dp)),
                contentAlignment = Alignment.Center
            ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Emoji or Icon
                Image(
                    painter = painterResource(id = R.drawable.bonk),
                    contentDescription = "Bonk",
                    modifier = Modifier.size(48.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Title
                Text(
                    text = "Bonk",
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Gain percentage
                Text(
                    text = "+0.2267%",
                    style = TextStyle(
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Green
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Date
                Text(
                    text = "Since\nNov 20, 2024",
                    textAlign = TextAlign.Center,
                    style = TextStyle(
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                )
            }
            }

            Image(
                painter = painterResource(id = R.drawable.dollar),
                contentDescription = "Dollar Sign",
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .size(48.dp)
                    .padding(top = 4.dp, start = 4.dp)
            )

            Image(
                painter = painterResource(id = R.drawable.dollar),
                contentDescription = "Dollar Sign",
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .size(96.dp)
                    .padding(bottom = 12.dp, start = 12.dp)
            )

            Image(
                painter = painterResource(id = R.drawable.dollar),
                contentDescription = "Dollar Sign",
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .size(96.dp)
                    .padding(top = 12.dp, start = 4.dp)
            )

            Image(
                painter = painterResource(id = R.drawable.dollar),
                contentDescription = "Dollar Sign",
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(bottom = 4.dp, start = 16.dp)
                    .size(48.dp)
            )
        }

    }
}