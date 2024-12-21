package com.example.tradingapp.compose.authentication

import android.content.Intent
import android.os.Build
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.tradingapp.compose.utils.CustomizedButton
import com.example.tradingapp.compose.utils.Title
import com.example.tradingapp.ui.theme.TradingAppTheme
import com.example.tradingapp.utils.BiometricPromptManager
import com.example.tradingapp.viewModels.authentication.AuthenticationViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun EmailScreen(
    isEmail: Boolean,
    promptManager: BiometricPromptManager,
    viewModel: AuthenticationViewModel = koinViewModel(),
    onButtonClick: (String) -> Unit,
) {
    TradingAppTheme {
        Scaffold(
            topBar = {
                Box(
                    modifier = Modifier
                        .padding(top = 45.dp)
                ) {
                    Title(title = "") {
                        onButtonClick.invoke("Back")
                    }
                }
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(paddingValues)
                    .padding(horizontal = 22.dp, vertical = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

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

                if (isEmail) EmailField(viewModel)
                else OtpInputField(viewModel = viewModel) {}

                Row(
                    verticalAlignment = Alignment.Bottom,
                    modifier = Modifier
                        .weight(1f)
                        .padding(16.dp)
                ) {
                    CustomizedButton(
                        "Next",
                        44,
                        null,
                        onButtonClick = {
                            onButtonClick.invoke("Button")
//                        viewModel.connectAccount{
//                            if(it) onButtonClick.invoke("Button")
//                        }
                        })
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

        if (viewModel.showBiometric) {
            BiometricAuthContent(
                biometricResult = biometricResult,
                promptManager = promptManager,
                count = count.intValue
            ) {
                count.intValue = it
            }
        }
    }
}






