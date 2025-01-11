package com.example.degn.compose.utils

import android.annotation.SuppressLint
import androidx.navigation.NavController

object GlobalNavController {
    @SuppressLint("StaticFieldLeak")
    var navController: NavController? = null
}