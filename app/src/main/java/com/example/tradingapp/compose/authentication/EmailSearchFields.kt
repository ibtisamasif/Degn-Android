package com.example.tradingapp.compose.authentication

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tradingapp.viewModels.authentication.AuthenticationViewModel

@Composable
fun EmailField(
    viewModel: AuthenticationViewModel
) {
    OutlinedTextField(
        value = viewModel.email.value,
        onValueChange = { viewModel.email.value = it },
        placeholder = {
            Text(
                text = "Enter email",
                style = MaterialTheme.typography.labelMedium
            )
        },
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .padding(horizontal = 16.dp)
            .drawBehind {
                val borderWidth = 1.dp.toPx()
                val cornerRadius = 12.dp.toPx()
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
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent,
            cursorColor = Color.Black,
            focusedTextColor = Color.Black,
            focusedPlaceholderColor = Color.Gray
        ),
        textStyle = MaterialTheme.typography.labelMedium
    )
}


@Composable
fun OtpInputField(
    otpLength: Int = 6,
    viewModel: AuthenticationViewModel,
    onOtpComplete: (String) -> Unit = {}
) {
    val otp by viewModel.otp
    val otpValues = otp.padEnd(otpLength, ' ').toList().map { it.toString() }
    val focusRequesters = List(otpLength) { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current
    val clipboardManager = LocalClipboardManager.current

    LaunchedEffect(Unit) {
        clipboardManager.getText()?.text?.let { clipboardText ->
            if (clipboardText.length == otpLength && clipboardText.all { it.isDigit() }) {
                viewModel.otp.value = clipboardText
                onOtpComplete(clipboardText)
            }
        }
    }

    Row(
        modifier = Modifier.padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        otpValues.forEachIndexed { index, value ->
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(44.dp)
                    .focusRequester(focusRequesters[index])
                    .drawBehind {
                        val borderWidth = 1.dp.toPx()
                        val cornerRadius = 12.dp.toPx()
                        drawRoundRect(
                            color = Color.Black,
                            size = size.copy(
                                width = size.width - borderWidth,
                                height = size.height - borderWidth
                            ),
                            cornerRadius = CornerRadius(cornerRadius, cornerRadius),
                            style = Stroke(width = borderWidth)
                        )
                    }
            ) {
                BasicTextField(
                    value = value,
                    onValueChange = { input ->
                        if (input.length <= 1 && input.all { it.isDigit() }) {
                            val updatedOtp = otpValues.toMutableList().apply {
                                set(index, input)
                            }.joinToString("")
                            viewModel.otp.value = updatedOtp

                            if (input.isEmpty()) {
                                if (index > 0) {
                                    focusRequesters[index - 1].requestFocus()
                                }
                            } else if (index < otpLength - 1) {
                                focusRequesters[index + 1].requestFocus()
                            }

                            if (updatedOtp.trim().length == otpLength) {
                                keyboardController?.hide()
                                onOtpComplete(updatedOtp)
                            }
                        }
                    },
                    textStyle = MaterialTheme.typography.labelLarge.copy(
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center,
                        color = Color.Black
                    ),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
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
                                onOtpComplete(otpValues.joinToString(""))
                            }
                        }
                    ),
                    decorationBox = { innerTextField ->
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            innerTextField()
                        }
                    }
                )
            }
        }
    }
}