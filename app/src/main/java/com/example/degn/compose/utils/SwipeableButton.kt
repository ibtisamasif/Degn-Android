package com.example.degn.compose.utils

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.degn.R
import com.example.degn.ui.theme.Grey
import com.example.degn.ui.theme.Purple
import kotlin.math.roundToInt

@Composable
fun ConfirmationButton(
    modifier: Modifier = Modifier,
    onConfirm: () -> Unit
) {
    val cornerRadius = 18.dp
    val maxDragPx = (LocalDensity.current.density * (350 - 120))
    val dragOffset = remember { mutableFloatStateOf(0f) }
    val progress = dragOffset.floatValue / maxDragPx

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp)
            .height(45.dp)
            .width(87.dp)
            .background(
                color = if (progress >= 0.8f) Purple else Grey,
                shape = RoundedCornerShape(cornerRadius)
            ),
    ) {
        if(progress >= 0.85f){
            CircularProgressIndicator(
                color = Color.White,
                strokeWidth = 4.dp,
                modifier = Modifier.size(32.dp).align(Alignment.Center)
            )
            onConfirm()
        }else{
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

            DraggableControl(
                modifier = Modifier
                    .offset {
                        IntOffset(dragOffset.floatValue.roundToInt(), 0)
                    }
                    .size(87.dp, 45.dp)
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(cornerRadius)
                    )
                    .draggable(
                        orientation = Orientation.Horizontal,
                        state = rememberDraggableState { delta ->
                            dragOffset.floatValue =
                                (dragOffset.floatValue + delta).coerceIn(0f, maxDragPx)
                        },
                        onDragStopped = {
                            if (progress >= 0.8f) {
                                dragOffset.floatValue = maxDragPx
                                onConfirm()
                            } else {
                                dragOffset.floatValue = 0f
                            }
                        }
                    ),
                progress = progress
            )
        }
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
            if (!isConfirmed) {
                Image(
                    painter = painterResource(id = R.drawable.double_arrow),
                    contentDescription = "Confirm Icon",
                    modifier = Modifier.size(14.dp)
                )
            }
        }
    }
}
