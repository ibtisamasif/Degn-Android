package com.example.degn.viewModels.settings

import androidx.lifecycle.ViewModel
import com.example.degn.di.pref.DegnSharedPref
import com.example.degn.di.pref.DegnSharedPref.Companion.CONST_IMAGE_URL
import com.example.degn.di.pref.DegnSharedPref.Companion.KEY_LOGIN_EMAIL
import com.example.degn.di.pref.DegnSharedPref.Companion.KEY_LOGIN_NAME

class SettingsViewModel(
    private val pref: DegnSharedPref
) : ViewModel() {
    var userName = pref.getString(KEY_LOGIN_NAME)
    var profileImage = pref.getString(CONST_IMAGE_URL)
    var userEmail = pref.getString(KEY_LOGIN_EMAIL)

    fun getUserDetail(){
        userName = pref.getString(KEY_LOGIN_NAME)
        profileImage = pref.getString(CONST_IMAGE_URL)
        userEmail = pref.getString(KEY_LOGIN_EMAIL)
    }
}