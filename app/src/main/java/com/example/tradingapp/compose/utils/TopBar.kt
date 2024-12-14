package com.example.tradingapp.compose.utils

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tradingapp.R

@Composable
fun TopBar(onSettingClicked: (Int) -> Unit) {
    var search by remember { mutableStateOf("") }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.height(64.dp))
        Icon(
            painter = painterResource(R.drawable.history),
            contentDescription = "History Icon",
            modifier = Modifier
                .padding(start = 16.dp)
                .size(20.dp)
                .clickable { onSettingClicked.invoke(5) }
        )

        Box(
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .height(43.dp)
                .padding(horizontal = 16.dp)
                .drawBehind {
                    val borderWidth = 1.dp.toPx()
                    val cornerRadius = 24.dp.toPx()
                    drawRoundRect(
                        color = Color.Black,
                        size = size.copy(
                            width = size.width - borderWidth,
                            height = size.height - borderWidth
                        ),
                        cornerRadius = CornerRadius(cornerRadius, cornerRadius),
                        style = Stroke(width = borderWidth)
                    )
                },
            contentAlignment = Alignment.CenterStart
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search Icon",
                    modifier = Modifier.size(18.dp),
                    tint = Color.Black
                )

                Spacer(modifier = Modifier.width(8.dp))

                BasicTextField(
                    value = search,
                    onValueChange = { search = it },
                    singleLine = true,
                    textStyle = MaterialTheme.typography.labelLarge.copy(
                        fontSize = 14.5.sp,
                        color = Color.Black,
                        textAlign = TextAlign.Start
                    ),
                    decorationBox = { innerTextField ->
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            // Placeholder
                            if (search.isEmpty()) {
                                Text(
                                    text = "Search",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontSize = 14.5.sp,
                                        color = Color.Gray
                                    )
                                )
                            }
                            innerTextField()
                        }
                    }
                )
            }
        }

        Icon(
            Icons.Default.Settings,
            contentDescription = "Settings Icon",
            modifier = Modifier
                .padding(end = 16.dp)
                .clickable { onSettingClicked.invoke(4) }
        )
    }
}