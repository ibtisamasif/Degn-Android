package com.example.tradingapp

import android.content.Intent
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

    private lateinit var moonPayBuySdk: MoonPayAndroidSdk
    private lateinit var moonPaySellSdk: MoonPayAndroidSdk
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        initializeMoonPayBuySDK()
        initializeMoonPaySellSDK()
        setContent {
            TradingAppTheme {
                NavController(activity = this, promptManager = promptManager){
                    when(it) {
                        "Buy"->showMoonPayBuySDK()
                        "Sell"->showMoonPaySellSDK()
                    }
                }
            }
        }
    }

    private fun initializeMoonPayBuySDK() {
        val handlers = createMoonPayHandlers()

        val params = MoonPayBuyQueryParams("pk_test_qXXVG9uQ3dRhOV386Y7xKiCg1nzgQd1K").apply {
            setCurrencyCode("ETH")
            setEmail("zeeshich019@gmail.com")
        }

        val config = MoonPaySdkBuyConfig(
            environment = MoonPayWidgetEnvironment.Sandbox,
            debug = true,
            params = params,
            handlers = handlers
        )

        moonPayBuySdk = MoonPayAndroidSdk(config = config, activity = this)


    }

    private fun initializeMoonPaySellSDK() {
        val handlers = createMoonPayHandlers()

        val sellParams = MoonPaySellQueryParams("pk_test_qXXVG9uQ3dRhOV386Y7xKiCg1nzgQd1K").apply {
            setCurrencyCode("ETH")
            setEmail("zeeshich019@gmail.com")
        }

        val config = MoonPaySdkSellConfig(
            environment = MoonPayWidgetEnvironment.Sandbox,
            debug = true,
            params = sellParams,
            handlers = handlers
        )

        moonPaySellSdk = MoonPayAndroidSdk(config = config, activity = this)
    }

    private fun createMoonPayHandlers(): MoonPayHandlers {
        return MoonPayHandlers(
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
    }

//    private fun getIntentData() {
//        try {
//            if (intent != null && intent.data != null) {
//                var link: String? = null
//                link = intent.data.toString()
//                if(link != null && link.isNotEmpty()){
//                    var token = link.substring(link.lastIndexOf("/") + 1) + ""
//                    when {
//                        link.contains(Constants.ApisEndPoint.resendAccountEmailVerificationEndPoint) -> {
//                            var intent = Intent(context, this)
//                            intent.putExtra("isFrom","splash")
//                            intent.putExtra("token",token+"")
//                            startActivity(intent)
//                            finishAffinity()
//                        }
//                        link.contains(Constants.ApisEndPoint.resendForgotEmailEndPoint) -> {
//                            var intent = Intent(context, ResetPasswordView::class.java)
//                            intent.putExtra("isFrom","splash")
//                            intent.putExtra("token",token+"")
//                            startActivity(intent)
//                            finishAffinity()
//                        }
//                        else -> {
//                            navigateScreen()
//                        }
//                    }
//                }
//            }else {
//                navigateScreen()
//            }
//        } catch (e: Exception) {
//            Log.e("error:", e.toString() + "")
//        }
//    }

    private fun showMoonPayBuySDK(){
        moonPayBuySdk.show(MoonPayRenderingOptionAndroid.WebViewOverlay)
    }
    private fun showMoonPaySellSDK(){
        moonPaySellSdk.show(MoonPayRenderingOptionAndroid.WebViewOverlay)
    }

}