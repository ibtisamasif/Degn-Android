package com.example.tradingapp.compose.payment

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tradingapp.ui.theme.Green
import com.example.tradingapp.ui.theme.Grey
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentScreen() {
    val bottomSheetState =
        rememberModalBottomSheetState()
    val coroutineScope = rememberCoroutineScope()

    ModalBottomSheet(
        sheetState = bottomSheetState,
        onDismissRequest = {}
    ) {
        MainContent {
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainContent(onShowBottomSheet: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Box(modifier = Modifier.weight(0.5f)) {}
            Text(
                "Send",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.weight(0.5f)
            )
            IconButton(onClick = { /* Handle close */ }) {
                Icon(Icons.Rounded.Close, contentDescription = "Close", tint = Color.Green)
            }
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
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp)
            ) {
                listOf("10%", "25%", "50%", "MAX").forEachIndexed { index, label ->
                    Button(
                        onClick = { /* Handle percentage selection */ },
                        colors = if (index == 1) ButtonDefaults.buttonColors(containerColor = Color.Green) else ButtonDefaults.buttonColors(
                            containerColor = Color.LightGray
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .defaultMinSize(minWidth = 64.dp)
                            .padding(4.dp)
                    ) {
                        Text(
                            text = label,
                            fontSize = 14.sp,
                            color = if (index == 1) Color.White else Color.Black
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Numeric Keypad
        NumericKeypad(onKeyPress = { /* Handle key press */ })

        Spacer(modifier = Modifier.weight(1f))
        Row(horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
            Spacer(modifier = Modifier.width(32.dp))
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
                            contentColor = MaterialTheme.colorScheme.onBackground,
                            containerColor = MaterialTheme.colorScheme.background
                        ),
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp),
                        modifier = Modifier.size(80.dp)
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
                color = Color.White,
                shape = RoundedCornerShape(18.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Crossfade(targetState = progress >= 0.8f, label = "") { isConfirmed ->
            if (isConfirmed) {
                Icon(
                    imageVector = Icons.Filled.Done,
                    contentDescription = "Confirm Icon",
                    tint = Green
                )
            } else {
                Icon(
                    imageVector = Icons.Filled.ArrowForward,
                    contentDescription = "Forward Icon",
                    tint = Green
                )
            }
        }
    }
}
