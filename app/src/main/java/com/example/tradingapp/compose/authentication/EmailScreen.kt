package com.example.tradingapp.compose.authentication

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.tradingapp.compose.utils.CustomizedButton
import com.example.tradingapp.compose.utils.Title
import com.example.tradingapp.ui.theme.Green
import com.example.tradingapp.ui.theme.TradingAppTheme

@Composable
fun EnterEmailScreen(isEmail: Boolean) {
    TradingAppTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Title(title = "") {}
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = if (isEmail) "Enter your email" else "Verify your email",
                style = MaterialTheme.typography.titleLarge
            )

            Text(
                text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed tristique vehicula purus.",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(horizontal = 32.dp, vertical = 16.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            if (isEmail) EmailField()
            else OtpInputField()

            Row(
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier.weight(1f)
            ) {
                CustomizedButton("Next", null)
            }
        }
    }
}

@Composable
fun EmailField() {
    var email by remember { mutableStateOf("") }
    OutlinedTextField(
        value = email,
        onValueChange = { email = it },
        placeholder = {
            Text(
                text = "Enter email",
                style = MaterialTheme.typography.bodySmall
            )
        },
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            cursorColor = Color.Black,
            focusedBorderColor = Color.Black,
            unfocusedBorderColor = Color.Black,
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black,
        ),
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun OtpInputField(
    otpLength: Int = 6,
    onOtpComplete: (String) -> Unit = {}
) {
    var otpValues by remember { mutableStateOf(List(otpLength) { "" }) }
    val focusRequesters = List(otpLength) { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current
    val clipboardManager = LocalClipboardManager.current

    LaunchedEffect(Unit) {
        clipboardManager.getText()?.text?.let { clipboardText ->
            if (clipboardText.length == otpLength && clipboardText.all { it.isDigit() }) {
                otpValues = clipboardText.map { it.toString() }
                onOtpComplete(clipboardText)
            }
        }
    }

    Row(
        modifier = Modifier.padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        otpValues.forEachIndexed { index, value ->
            OutlinedTextField(
                value = value,
                onValueChange = { input ->
                    if (input.length <= 1 && input.all { it.isDigit() }) {
                        otpValues = otpValues.toMutableList().apply { set(index, input) }

                        if (input.isNotEmpty() && index < otpLength - 1) {
                            focusRequesters[index + 1].requestFocus()
                        }

                        if (otpValues.all { it.isNotEmpty() }) {
                            keyboardController?.hide()
                            onOtpComplete(otpValues.joinToString(""))
                        }
                    }
                },
                modifier = Modifier
                    .size(48.dp)
                    .focusRequester(focusRequesters[index]),
                textStyle = MaterialTheme.typography.bodyMedium,
                colors = OutlinedTextFieldDefaults.colors(
                    cursorColor = Color.Black,
                    focusedBorderColor = Color.Black,
                    unfocusedBorderColor = Color.Black,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
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
                )
            )
        }
    }
}


