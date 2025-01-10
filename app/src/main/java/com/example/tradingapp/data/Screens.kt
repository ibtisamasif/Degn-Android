package com.example.tradingapp.data

sealed class Screens(val route: String){
    data object SplashScreen : Screens("SplashScreen")
    data object SliderScreen : Screens("SliderScreen")
    data object EmailScreen : Screens("EmailScreen")
    data object OTPScreen : Screens("OTPScreen")
    data object HomeScreen : Screens("HomeScreen")
    data object ActivityScreen : Screens("ActivityScreen")
    data object TransactionDetailScreen : Screens("TransactionDetailScreen")
    data object TrendingScreen : Screens("TrendingScreen")
    data object CoinDetailScreen : Screens("CoinDetailScreen")
    data object RewardScreen : Screens("RewardScreen")
    data object WalletScreen : Screens("WalletScreen")
    data object ProfileScreen : Screens("ProfileScreen")
    data object SettingScreen : Screens("SettingScreen")
    data object NotificationScreen : Screens("NotificationScreen")
    data object LegalScreen : Screens("LegalScreen")
    data object ExportScreen : Screens("ExportScreen")
    data object VerifyOTPScreen : Screens("VerifyOTPScreen")
}