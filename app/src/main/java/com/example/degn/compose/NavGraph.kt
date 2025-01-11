package com.example.degn.compose

import SplashScreen
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.degn.MainActivity
import com.example.degn.compose.authentication.EmailScreen
import com.example.degn.compose.export.ExportKeysScreen
import com.example.degn.compose.export.VerifyOTP
import com.example.degn.compose.home.ActivityScreen
import com.example.degn.compose.home.CoinDetailScreen
import com.example.degn.compose.home.HomeScreen
import com.example.degn.compose.home.TransactionDetailUI
import com.example.degn.compose.notification.NotificationScreen
import com.example.degn.compose.profile.ProfileScreen
import com.example.degn.compose.rewards.RewardScreen
import com.example.degn.compose.setting.SettingsScreen
import com.example.degn.compose.slider.SliderScreen
import com.example.degn.compose.support.LegalAndPrivacy
import com.example.degn.compose.utils.GlobalNavController
import com.example.degn.compose.wallet.WalletScreen
import com.example.degn.data.Screens
import com.example.degn.utils.BiometricPromptManager

@Composable
fun NavController(
    activity: MainActivity,
    promptManager: BiometricPromptManager,
    openMoonPaySDK: (String) -> Unit
) {
    val navController = rememberNavController()
    GlobalNavController.navController = navController
    NavHost(
        navController = navController,
        startDestination = Screens.SplashScreen.route,
    ) {
        composable(route = Screens.SplashScreen.route) {
            SplashScreen(navController = navController)
        }

        composable(route = Screens.SliderScreen.route) {
            SliderScreen {
                navController.navigate(Screens.EmailScreen.route)
            }
        }

        composable(route = Screens.EmailScreen.route) {
            EmailScreen(true, promptManager) {
                when (it) {
                    "Biometric" -> navController.navigate(Screens.HomeScreen.route)
                    "Back" -> navController.navigateUp()
                    else -> navController.navigate(Screens.OTPScreen.route)
                }
            }
        }

        composable(route = Screens.OTPScreen.route) {
            EmailScreen(false, promptManager) {
                if (it == "Back") navController.navigateUp()
                else navController.navigate(Screens.HomeScreen.route)
            }
        }

        composable(route = Screens.HomeScreen.route) {
            HomeScreen(isHome = true) {
                navController.navigate(it)
            }
        }

        composable(route = Screens.TrendingScreen.route) {
            HomeScreen(isHome = false) {
                navController.navigate(it)
            }
        }

        composable(route = Screens.RewardScreen.route) {
            RewardScreen {
                navController.navigate(it)
            }
        }

        composable(route = Screens.WalletScreen.route) {
            WalletScreen {
                when (it) {
                    "Sell" -> openMoonPaySDK.invoke("Sell")
                    "Buy" -> openMoonPaySDK.invoke("Buy")
                    else -> navController.navigate(it)
                }
            }
        }

        composable(route = Screens.SettingScreen.route) {
            SettingsScreen {
                when (it) {
                    "Notifications" -> navController.navigate(Screens.NotificationScreen.route)
                    "Export Keys" -> navController.navigate(Screens.ExportScreen.route)
                    "Legal & Privacy" -> navController.navigate(Screens.LegalScreen.route)
                    "Profile" -> navController.navigate(Screens.ProfileScreen.route)
                    "Back" -> navController.navigateUp()
                }
            }
        }

        composable(route = Screens.LegalScreen.route) {
            LegalAndPrivacy {
                navController.navigateUp()
            }
        }

        composable(route = Screens.ExportScreen.route) {
            ExportKeysScreen {
                when (it) {
                    "SecretKey" -> navController.navigate(Screens.VerifyOTPScreen.route)
                    "Back" -> navController.navigateUp()
                }
            }
        }

        composable(route = Screens.NotificationScreen.route) {
            NotificationScreen {
                navController.navigateUp()
            }
        }

        composable(route = Screens.ProfileScreen.route) {
            ProfileScreen {
                navController.navigateUp()
            }
        }

        composable(route = Screens.ActivityScreen.route) {
            ActivityScreen {
                when (it) {
                    "Back" -> navController.navigateUp()
                    else -> navController.navigate(Screens.TransactionDetailScreen.route)
                }

            }
        }

        composable(route = Screens.CoinDetailScreen.route + "/{tokenId}") { backStackEntry ->
            val tokenId = backStackEntry.arguments?.getString("tokenId") ?: ""
            CoinDetailScreen(tokenId = tokenId) {
                navController.navigateUp()
            }
        }


        composable(route = Screens.TransactionDetailScreen.route) {
            TransactionDetailUI {
                navController.navigateUp()
            }
        }

        composable(route = Screens.VerifyOTPScreen.route) {
            VerifyOTP {
                navController.navigateUp()
            }
        }
    }
}