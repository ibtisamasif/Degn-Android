package com.example.tradingapp.compose

import SplashScreen
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tradingapp.MainActivity
import com.example.tradingapp.compose.authentication.EmailScreen
import com.example.tradingapp.compose.home.HomeScreen
import com.example.tradingapp.compose.rewards.RewardScreen
import com.example.tradingapp.data.Screens
import com.example.tradingapp.utils.BiometricPromptManager

@Composable
fun NavController(activity: MainActivity,promptManager: BiometricPromptManager){
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screens.SplashScreen.route,
    ) {
        composable(route = Screens.SplashScreen.route) {
            SplashScreen(navController)
        }

        composable(route = Screens.EmailScreen.route) {
            EmailScreen(true,promptManager){
                navController.navigate(Screens.OTPScreen.route)
            }
        }

        composable(route = Screens.OTPScreen.route) {
            EmailScreen(false,promptManager){
                navController.navigate(Screens.HomeScreen.route)
            }
        }

        composable(route = Screens.HomeScreen.route) {
            HomeScreen(true)
        }

        composable(route = Screens.TrendingScreen.route) {
            HomeScreen(false)
        }

        composable(route = Screens.RewardScreen.route) {
            RewardScreen()
        }

        composable(route = Screens.WalletScreen.route) {
            RewardScreen()
        }

    }
}