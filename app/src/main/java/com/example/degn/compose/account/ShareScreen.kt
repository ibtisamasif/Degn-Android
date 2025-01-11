package com.example.degn.compose.account

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.degn.R
import com.example.degn.compose.utils.CustomizedButton
import com.example.degn.compose.utils.Title

@SuppressLint("InvalidColorHexValue")
@Composable
fun GainsScreen(onCloseBottomSheet: (Boolean) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.99f)
            .background(Color.White)
    ) {
        Title(title = "", isBottomSheet = true) {
            onCloseBottomSheet.invoke(true)
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(horizontal = 16.dp)
        ) {
            CustomizedButton("Share gains", 64, R.drawable.share) {}
        }

        // Central Card
        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .size(300.dp)
        ) {
            Box(
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(260.dp)
                    .clip(RoundedCornerShape(16.dp))
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
                        text = "Bonk", style = TextStyle(
                            fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.Black
                        )
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Gain percentage
                    Text(
                        text = "+0.2267%", style = TextStyle(
                            fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.Green
                        )
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Date
                    Text(
                        text = "Since\nNov 20, 2024",
                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            fontSize = 14.sp, color = Color.Gray
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
