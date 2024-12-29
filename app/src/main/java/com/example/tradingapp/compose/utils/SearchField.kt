package com.example.tradingapp.compose.utils

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tradingapp.ui.theme.gradient

@Composable
fun SearchField(
    editable: Boolean = false,
    initialValue: String = "",
    placeholder: String = "Search",
    onClick: (() -> Unit)? = null,
    onValueChange: (String) -> Unit = {},
) {
    var text by remember { mutableStateOf(initialValue) }
    val interactionSource = remember { MutableInteractionSource() }
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    Box(
        modifier = Modifier
            .fillMaxWidth(0.85f)
            .height(43.dp)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                enabled = !editable && onClick != null
            ) {
                onClick?.invoke()
            }
            .padding(horizontal = 16.dp)
            .drawBehind {
                val borderWidth = 1.dp.toPx()
                val cornerRadius = 24.dp.toPx()
                val borderBrush = if (editable) gradient else Brush.linearGradient(
                    colors = listOf(Color.Black, Color.Black),
                    start = Offset.Zero,
                    end = Offset.Infinite,
                )
                drawRoundRect(
                    brush = borderBrush,
                    size = size.copy(
                        width = size.width - borderWidth,
                        height = size.height - borderWidth
                    ),
                    cornerRadius = CornerRadius(cornerRadius, cornerRadius),
                    style = Stroke(width = borderWidth)
                )
            }
            .fillMaxWidth()
            .focusRequester(focusRequester),
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

            if (editable) {
                BasicTextField(
                    value = text,
                    onValueChange = {
                        text = it
                        onValueChange(it)
                    },
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
                            if (text.isEmpty()) {
                                Text(
                                    text = placeholder,
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
            } else {
                Text(
                    text = placeholder,
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontSize = 14.5.sp,
                        color = Color.Gray
                    )
                )
            }
        }
    }
}
