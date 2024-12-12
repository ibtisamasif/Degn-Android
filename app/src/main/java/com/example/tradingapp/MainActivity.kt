package com.example.tradingapp

import SplashScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.tradingapp.compose.NavController
import com.example.tradingapp.compose.account.GainsScreen
import com.example.tradingapp.compose.authentication.EmailScreen
import com.example.tradingapp.compose.export.ExportKeysScreen
import com.example.tradingapp.compose.home.ActivityScreen
import com.example.tradingapp.compose.home.HomeScreen
import com.example.tradingapp.compose.notification.NotificationScreen
import com.example.tradingapp.compose.payment.PaymentScreen
import com.example.tradingapp.compose.support.LegalAndPrivacy
import com.example.tradingapp.compose.support.SupportScreen
import com.example.tradingapp.compose.wallet.WalletScreen
import com.example.tradingapp.ui.theme.TradingAppTheme
import com.example.tradingapp.utils.BiometricPromptManager

class MainActivity : AppCompatActivity() {
    private val promptManager by lazy {
        BiometricPromptManager(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TradingAppTheme {
//                NavController(activity = this,promptManager=promptManager)
                WalletScreen()
            }
        }
    }
}