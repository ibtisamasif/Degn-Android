package com.example.tradingapp.compose.authentication

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.tradingapp.utils.BiometricPromptManager

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