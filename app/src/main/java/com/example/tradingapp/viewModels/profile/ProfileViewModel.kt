package com.example.tradingapp.viewModels.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tradingapp.data.User
import com.example.tradingapp.di.pref.DegnSharedPref
import com.example.tradingapp.repo.ProfileRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val profileRepo: ProfileRepo,
    private val pref: DegnSharedPref
) : ViewModel() {
    private val _userProfile = MutableStateFlow<User?>(null)
    val userProfile: StateFlow<User?> = _userProfile

    fun getProfile() {
        viewModelScope.launch {
            val response = pref.getAccessToken()?.let { profileRepo.fetchUserProfile(it) }
            _userProfile.value = response?.body?.user
        }
    }
}