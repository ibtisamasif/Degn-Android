package com.example.tradingapp.compose

import SplashScreen
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tradingapp.MainActivity
import com.example.tradingapp.compose.authentication.EmailScreen
import com.example.tradingapp.compose.export.ExportKeysScreen
import com.example.tradingapp.compose.home.ActivityScreen
import com.example.tradingapp.compose.home.HomeScreen
import com.example.tradingapp.compose.notification.NotificationScreen
import com.example.tradingapp.compose.profile.ProfileScreen
import com.example.tradingapp.compose.rewards.RewardScreen
import com.example.tradingapp.compose.setting.SettingsScreen
import com.example.tradingapp.compose.slider.SliderScreen
import com.example.tradingapp.compose.support.LegalAndPrivacy
import com.example.tradingapp.compose.wallet.WalletScreen
import com.example.tradingapp.data.Screens
import com.example.tradingapp.utils.BiometricPromptManager

@Composable
fun NavController(activity: MainActivity, promptManager: BiometricPromptManager) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screens.SplashScreen.route,
    ) {
        composable(route = Screens.SplashScreen.route) {
            SplashScreen(navController)
        }

        composable(route = Screens.SliderScreen.route) {
            SliderScreen{
                navController.navigate(Screens.EmailScreen.route)
            }
        }

        composable(route = Screens.EmailScreen.route) {
            EmailScreen(true, promptManager) {
                navController.navigate(Screens.OTPScreen.route)
            }
        }

        composable(route = Screens.OTPScreen.route) {
            EmailScreen(false, promptManager) {
                navController.navigate(Screens.HomeScreen.route)
            }
        }

        composable(route = Screens.HomeScreen.route) {
            HomeScreen(true) {
                when (it) {
                    1 -> navController.navigate(Screens.TrendingScreen.route)
                    2 -> navController.navigate(Screens.RewardScreen.route)
                    3 -> navController.navigate(Screens.WalletScreen.route)
                    4 -> navController.navigate(Screens.SettingScreen.route)
                    5 -> navController.navigate(Screens.ActivityScreen.route)
                }
            }
        }

        composable(route = Screens.TrendingScreen.route) {
            HomeScreen(false) {
                when (it) {
                    0 -> navController.navigate(Screens.HomeScreen.route)
                    2 -> navController.navigate(Screens.RewardScreen.route)
                    3 -> navController.navigate(Screens.WalletScreen.route)
                    4 -> navController.navigate(Screens.SettingScreen.route)
                    5 -> navController.navigate(Screens.ActivityScreen.route)
                }
            }
        }

        composable(route = Screens.RewardScreen.route) {
            RewardScreen {
                when (it) {
                    0 -> navController.navigate(Screens.HomeScreen.route)
                    1 -> navController.navigate(Screens.TrendingScreen.route)
                    3 -> navController.navigate(Screens.WalletScreen.route)
                    4 -> navController.navigate(Screens.SettingScreen.route)
                    5 -> navController.navigate(Screens.ActivityScreen.route)
                }
            }
        }

        composable(route = Screens.WalletScreen.route) {
            WalletScreen {
                when (it) {
                    0 -> navController.navigate(Screens.HomeScreen.route)
                    1 -> navController.navigate(Screens.TrendingScreen.route)
                    2 -> navController.navigate(Screens.RewardScreen.route)
                    4 -> navController.navigate(Screens.SettingScreen.route)
                    5 -> navController.navigate(Screens.ActivityScreen.route)
                }
            }
        }

        composable(route = Screens.SettingScreen.route) {
            SettingsScreen{
                when(it){
                    "Notifications"-> navController.navigate(Screens.NotificationScreen.route)
                    "Export Keys"-> navController.navigate(Screens.ExportScreen.route)
                    "Legal & Privacy"-> navController.navigate(Screens.LegalScreen.route)
                    "Profile"-> navController.navigate(Screens.ProfileScreen.route)
                }
            }
        }

        composable(route = Screens.LegalScreen.route) {
            LegalAndPrivacy()
        }

        composable(route = Screens.ExportScreen.route) {
            ExportKeysScreen()
        }

        composable(route = Screens.NotificationScreen.route) {
            NotificationScreen()
        }

        composable(route = Screens.ProfileScreen.route) {
            ProfileScreen()
        }

        composable(route = Screens.ActivityScreen.route) {
            ActivityScreen()
        }
    }
}