package com.example.degn.repo

import androidx.navigation.navOptions
import com.example.degn.compose.utils.GlobalNavController
import com.example.degn.data.Screens
import com.example.degn.di.pref.DegnSharedPref
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