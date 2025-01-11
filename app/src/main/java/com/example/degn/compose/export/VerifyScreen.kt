package com.example.degn.compose.export

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.degn.compose.utils.CustomOtpInputField
import com.example.degn.compose.utils.Title
import com.example.degn.viewModels.export.ExportViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun VerifyOTP(
    viewModel: ExportViewModel = koinViewModel(),
    onBackPress: () -> Unit
){
    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .padding(top = 45.dp)
            ) {
                Title(title = "") {
                    onBackPress.invoke()
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
                text = "VERIFY YOUR EMAIL",
                style = MaterialTheme.typography.headlineMedium
            )

            Text(
                text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed tristique vehicula purus.",
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier.padding(vertical = 16.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            CustomOtpInputField(
                onValueChange = { viewModel.otp.value = it },
                onOtpComplete = {viewModel.verifyOtp{
                    viewModel.isShowSecretKey.value = true
                    onBackPress.invoke()
                }}
            )
        }
    }
}