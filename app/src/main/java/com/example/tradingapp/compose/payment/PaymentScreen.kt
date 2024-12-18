package com.example.tradingapp.compose.payment

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.example.tradingapp.ui.theme.Grey
import com.example.tradingapp.ui.theme.Purple
import kotlin.math.roundToInt

@Composable
fun PaymentScreen(onCloseBottomSheet: (Boolean) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Box(
                modifier = Modifier
                    .weight(.25f)
                    .align(Alignment.CenterVertically)
            ) {}
            Text(
                "Send",
                modifier = Modifier.weight(.75f),
                style = MaterialTheme.typography.titleMedium.copy(fontSize = 18.sp),
            )
            Image(
                painter = painterResource(R.drawable.close),
                contentDescription = "Close",
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .size(36.dp)
                    .clickable { onCloseBottomSheet.invoke(true) }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Balance and Input Amount
        Column(
            modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(12.dp))
                    .padding(1.dp)
            ) {
                Box(
                    modifier = Modifier
                        .background(
                            color = if (isSystemInDarkTheme()) Color(0xFF383838) else Color.Transparent,
                            shape = RoundedCornerShape(12.dp)
                        )
                        .padding(horizontal = 16.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "Cash: $0.00",
                        color = if (isSystemInDarkTheme()) Color.White else Color.Black,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "$0", fontSize = 48.sp, fontWeight = FontWeight.Bold, color = Color.Gray)

            // Percentage Buttons
            var selectedPercentage by remember { mutableStateOf("25%") }

            // Row with buttons
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp)
            ) {
                // List of labels for buttons
                listOf("10%", "25%", "50%", "MAX").forEachIndexed { index, label ->
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .padding(4.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .size(36.dp)
                            .background(if (selectedPercentage == label) Purple else Color.White)
                            .clickable { selectedPercentage = label }
                    ) {
                        Text(
                            text = label,
                            fontSize = 12.sp,
                            color = if (selectedPercentage == label) Color.White else Color.Black,
                            modifier = Modifier
                                .fillMaxSize() // Make Text take up all available space
                                .wrapContentSize(align = Alignment.Center) // Center the text
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Numeric Keypad
        NumericKeypad(onKeyPress = { /* Handle key press */ })

        Spacer(modifier = Modifier.weight(0.3f))
        Row(
            modifier = Modifier
                .weight(0.8f)
                .padding(16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            ConfirmationButton()
        }

    }
}

@Composable
fun NumericKeypad(onKeyPress: (String) -> Unit) {
    val keys = listOf(
        listOf("1", "2", "3"), listOf("4", "5", "6"), listOf("7", "8", "9"), listOf(".", "0", "")
    )

    Column(
        modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        keys.forEach { row ->
            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                row.forEach { key ->
                    Button(
                        onClick = { onKeyPress(key) },
                        shape = CircleShape,
                        colors = ButtonDefaults.buttonColors(
                            contentColor = Color.Black,
                            containerColor = Color.White
                        ),
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp),
                        modifier = Modifier.size(68.dp)
                    ) {
                        if (key !== "") {
                            Text(text = key, fontSize = 36.sp)
                        } else {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Clear",
                                modifier = Modifier.size(36.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun ConfirmationButton(modifier: Modifier = Modifier) {
    val width = 350.dp
    val dragSize = 50.dp
    val cornerRadius = 18.dp
    val maxDragPx = with(LocalDensity.current) { (width - dragSize).toPx() }
    val dragOffset = remember { mutableStateOf(0f) }
    val progress = dragOffset.value / maxDragPx
    val isConfirmed = remember { derivedStateOf { progress >= 0.8f } }

    Box(
        modifier = modifier
            .width(width)
            .height(dragSize)
            .background(
                color = Grey,
                shape = RoundedCornerShape(cornerRadius)
            ),
    ) {
        Column(
            Modifier
                .align(Alignment.Center)
                .alpha(1f - progress),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Swipe to Start",
                color = Color.White,
                fontSize = 12.sp
            )
        }

        // Draggable Control
        DraggableControl(
            modifier = Modifier
                .offset {
                    IntOffset(dragOffset.value.roundToInt(), 0)
                }
                .size(dragSize)
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(cornerRadius)
                )
                .draggable(
                    orientation = Orientation.Horizontal,
                    state = rememberDraggableState { delta ->
                        dragOffset.value = (dragOffset.value + delta).coerceIn(0f, maxDragPx)
                    },
                    onDragStopped = {
                        if (progress >= 0.8f) {
                            dragOffset.value = maxDragPx
                        } else {
                            dragOffset.value = 0f
                        }
                    }
                ),
            progress = progress
        )
    }
}

@Composable
private fun DraggableControl(
    modifier: Modifier,
    progress: Float
) {
    Box(
        modifier = modifier
            .shadow(
                elevation = 2.dp,
                shape = RoundedCornerShape(18.dp),
                clip = false
            )
            .background(
                color = Purple,
                shape = RoundedCornerShape(18.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Crossfade(targetState = progress >= 0.8f, label = "") { isConfirmed ->
            if (isConfirmed) {
                Icon(
                    imageVector = Icons.Filled.Done,
                    contentDescription = "Confirm Icon",
                    tint = Color.White
                )
            } else {
                Icon(
                    imageVector = Icons.Filled.ArrowForward,
                    contentDescription = "Forward Icon",
                    tint = Color.White
                )
            }
        }
    }
}
