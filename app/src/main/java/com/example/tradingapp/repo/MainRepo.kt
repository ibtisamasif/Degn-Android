package com.example.tradingapp.repo

import androidx.navigation.navOptions
import com.example.tradingapp.compose.utils.GlobalNavController
import com.example.tradingapp.data.Screens
import com.example.tradingapp.di.pref.DegnSharedPref
import retrofit2.Response

open class MainRepo(private val pref: DegnSharedPref) {
    fun handleApiError(response: Response<*>): Boolean {
        if (response.code() == 401) {
            pref.clearSession()
            GlobalNavController.navController?.navigate(Screens.EmailScreen.route,
                navOptions = navOptions {
                    popUpTo(Screens.EmailScreen.route) { inclusive = true }
                })
            return true
        }
        return false
    }
}