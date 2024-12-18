package com.example.tradingapp.compose.authentication

import android.content.Intent
import android.os.Build
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
import androidx.compose.material3.Button
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
import com.example.tradingapp.ui.theme.TradingAppTheme
import com.example.tradingapp.utils.BiometricPromptManager
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

@Composable
fun EmailScreen(isEmail: Boolean,promptManager: BiometricPromptManager,onButtonClick: (String)-> Unit) {
    TradingAppTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(horizontal = 22.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Title(title = "") {
                    onButtonClick.invoke("Back")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = if (isEmail) "ENTER YOUR EMAIL" else "VERIFY YOUR EMAIL",
                style = MaterialTheme.typography.headlineMedium
            )

            Text(
                text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed tristique vehicula purus.",
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier.padding(vertical = 16.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            if (isEmail) EmailField()
            else OtpInputField()

            Row(
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier.weight(1f).padding(16.dp)
            ) {
                CustomizedButton("Next", 64,null, onButtonClick = {onButtonClick.invoke("Button")})
            }
        }
    }

    val biometricResult by promptManager.promptResults.collectAsState(initial = null)
    val enrollLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = { onButtonClick.invoke("Button") }
    )
    val count = remember { mutableIntStateOf(0) }

    LaunchedEffect(biometricResult) {
        if (biometricResult is BiometricPromptManager.BiometricResult.AuthenticationNotSet) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                val enrollIntent = Intent(Settings.ACTION_BIOMETRIC_ENROLL).apply {
                    putExtra(
                        Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED,
                        BIOMETRIC_STRONG or DEVICE_CREDENTIAL
                    )
                }
                enrollLauncher.launch(enrollIntent)
            }
        }
    }

    if(!isEmail) {
        BiometricAuthContent(
            biometricResult = biometricResult,
            promptManager = promptManager,
            count = count.intValue
        ){
            count.intValue = it
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
            focusedTextColor  = Color.Black,
            focusedPlaceholderColor = Color.Gray
        ),
        textStyle = MaterialTheme.typography.labelMedium
    )
}


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
                            otpValues = otpValues.toMutableList().apply {
                                set(index, input)
                            }

                            if (input.isEmpty()) {
                                if (index > 0) {
                                    focusRequesters[index - 1].requestFocus()
                                }
                            } else if (index < otpLength - 1) {
                                focusRequesters[index + 1].requestFocus()
                            }

                            if (otpValues.all { it.isNotEmpty() }) {
                                keyboardController?.hide()
                                onOtpComplete(otpValues.joinToString(""))
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



@Composable
fun BiometricAuthContent(
    biometricResult: BiometricPromptManager.BiometricResult?,
    promptManager: BiometricPromptManager,
    count: Int,
    onSuccess: (Int) -> Unit
) {
    var isPromptShown by remember { mutableStateOf(false) }

    LaunchedEffect(biometricResult) {
        if (biometricResult is BiometricPromptManager.BiometricResult.AuthenticationSuccess) {
            onSuccess(count + 1)
        }
    }

    if (count < 1 && !isPromptShown) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            promptManager.showBiometricPrompt(
                title = "Authenticate",
                description = "Authenticate with biometrics to proceed."
            )
            isPromptShown = true
        }
    }
}





