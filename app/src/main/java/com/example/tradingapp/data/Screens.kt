package com.example.tradingapp.data

sealed class Screens(val route: String){
    data object SplashScreen : Screens("SplashScreen")
    data object EmailScreen : Screens("EmailScreen")
    data object OTPScreen : Screens("OTPScreen")
    data object HomeScreen : Screens("HomeScreen")
    data object TrendingScreen : Screens("TrendingScreen")
    data object RewardScreen : Screens("RewardScreen")
    data object WalletScreen : Screens("WalletScreen")
}