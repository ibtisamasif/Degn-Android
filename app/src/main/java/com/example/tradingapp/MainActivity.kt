package com.example.tradingapp

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.tradingapp.compose.NavController
import com.example.tradingapp.ui.theme.TradingAppTheme
import com.example.tradingapp.utils.BiometricPromptManager
import com.moonpay.sdk.MoonPayAndroidSdk
import com.moonpay.sdk.MoonPayBuyQueryParams
import com.moonpay.sdk.MoonPayHandlers
import com.moonpay.sdk.MoonPayRenderingOptionAndroid
import com.moonpay.sdk.MoonPaySdkBuyConfig
import com.moonpay.sdk.MoonPaySdkSellConfig
import com.moonpay.sdk.MoonPaySellQueryParams
import com.moonpay.sdk.MoonPayWidgetEnvironment
import com.moonpay.sdk.OnInitiateDepositResponsePayload

class MainActivity : AppCompatActivity() {
    private val promptManager by lazy {
        BiometricPromptManager(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        initializeMoonPaySDK()
        enableEdgeToEdge()
        setContent {
            TradingAppTheme {
                NavController(activity = this, promptManager = promptManager)
            }
        }
    }

    private fun initializeMoonPaySDK() {

        //
//        val params = MoonPayBuyQueryParams(apiKey = "pk_test_qXXVG9uQ3dRhOV386Y7xKiCg1nzgQd1K").apply {
//            setCurrencyCode("SOL")
//            setEmail("ibtisam283@gmail.com")
//        }
//
//
//        val config = MoonPaySdkBuyConfig(
//            environment = MoonPayWidgetEnvironment.Sandbox,
//            debug = true,
//            params = params,
//            handlers = handlers
//        )
//
//        val moonPaySdk = MoonPayAndroidSdk(config = config, activity = this)
//
//        moonPaySdk.show(MoonPayRenderingOptionAndroid.WebViewOverlay)


        val handlers = MoonPayHandlers(
            onSwapsCustomerSetupComplete = { Log.i("HANDLER CALLED", "onSwapsCustomerSetupComplete called!") },
            onAuthToken = { Log.i("HANDLER CALLED", "onAuthToken called with payload $it") },
            onLogin = { Log.i("HANDLER CALLED", "onLogin called with payload $it") },
            onInitiateDeposit = {
                Log.i("HANDLER CALLED", "onInitiateDeposit called with payload $it")
                OnInitiateDepositResponsePayload(depositId = "someDepositId")
            },
            onKmsWalletCreated = { Log.i("HANDLER CALLED", "onKmsWalletCreated called!") },
            onUnsupportedRegion = { Log.i("HANDLER CALLED", "onUnsupportedRegion called!") }
        )

        val params = MoonPayBuyQueryParams("pk_test_qXXVG9uQ3dRhOV386Y7xKiCg1nzgQd1K")
        params.setCurrencyCode("ETH")

        val sellParams = MoonPaySellQueryParams("pk_test_qXXVG9uQ3dRhOV386Y7xKiCg1nzgQd1K")
        sellParams.setCurrencyCode("ETH")

        val config = MoonPaySdkSellConfig(
            environment = MoonPayWidgetEnvironment.Sandbox,
            debug = true,
            params = sellParams,
            handlers = handlers
        )

        // Initialize the SDK using 'this' reference for MainActivity
        val moonPaySdk = MoonPayAndroidSdk(config = config, activity = this)

        moonPaySdk.show(MoonPayRenderingOptionAndroid.WebViewOverlay)
    }
}