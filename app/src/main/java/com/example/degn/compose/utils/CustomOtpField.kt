package com.example.degn.compose.utils

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CustomOtpInputField(
    otpLength: Int = 6,
    onValueChange: (String) -> Unit,
    placeholder: String = "",
    textStyle: TextStyle = MaterialTheme.typography.labelLarge.copy(
        fontSize = 18.sp,
        textAlign = TextAlign.Center,
        color = Color.Black
    ),
    borderColor: Color = Color.Black,
    cornerRadius: Dp = 12.dp,
    boxSize: Dp = 44.dp,
    onOtpComplete: ((String) -> Unit)? = null
) {
    var otpValues by remember { mutableStateOf(List(otpLength) { "" }) }
    val focusRequesters = List(otpLength) { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    val clipboardManager = LocalClipboardManager.current

    val clipboardText = clipboardManager.getText()?.text ?: ""
    if (clipboardText.length == otpLength && clipboardText.all { it.isDigit() }) {
        otpValues = clipboardText.map { it.toString() }
        onValueChange(clipboardText)
        onOtpComplete?.invoke(clipboardText)
        keyboardController?.hide()
    }

    Row(
        modifier = Modifier.padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        otpValues.forEachIndexed { index, char ->
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(boxSize)
                    .focusRequester(focusRequesters[index])
                    .drawBehind {
                        val borderWidth = 1.dp.toPx()
                        val radius = cornerRadius.toPx()
                        drawRoundRect(
                            color = borderColor,
                            size = size.copy(
                                width = size.width - borderWidth,
                                height = size.height - borderWidth
                            ),
                            cornerRadius = CornerRadius(radius, radius),
                            style = Stroke(width = borderWidth)
                        )
                    }
            ) {
                BasicTextField(
                    value = char,
                    onValueChange = { input ->
                        if (input.length <= 1 && input.all { it.isDigit() }) {
                            otpValues = otpValues.toMutableList().apply {
                                set(index, input)
                            }
                            if (otpValues.all { it.isNotEmpty() }) {
                                keyboardController?.hide()
                                val completeOtp = otpValues.joinToString("")
                                onValueChange(completeOtp)
                                onOtpComplete?.invoke(completeOtp)
                            } else if (input.isNotEmpty() && index < otpLength - 1) {
                                focusRequesters[index + 1].requestFocus()
                            }
                        }
                    },
                    textStyle = textStyle,
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number,
                        imeAction = if (index == otpLength - 1) ImeAction.Done else ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = {
                            if (index < otpLength - 1) {
                                focusRequesters[index + 1].requestFocus()
                            }
                        },
                        onDone = {
                            keyboardController?.hide()
                            if (otpValues.all { it.isNotEmpty() }) {
                                val completeOtp = otpValues.joinToString("")
                                onValueChange(completeOtp)
                                onOtpComplete?.invoke(completeOtp)
                            }
                        }
                    ),
                    decorationBox = { innerTextField ->
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            if (char.isEmpty() && placeholder.isNotEmpty()) {
                                Text(
                                    text = placeholder,
                                    style = textStyle.copy(color = Color.Gray)
                                )
                            }
                            innerTextField()
                        }
                    }
                )
            }
        }
    }
}

