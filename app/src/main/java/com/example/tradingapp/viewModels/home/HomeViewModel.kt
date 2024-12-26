package com.example.tradingapp.viewModels.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tradingapp.data.TokenDetails
import com.example.tradingapp.di.pref.DegnSharedPref
import com.example.tradingapp.repo.DashboardRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val dashboardRepo: DashboardRepo,
    private val pref: DegnSharedPref
) : ViewModel() {

    private val _spotlightTokens = MutableStateFlow<List<TokenDetails>?>(null)
    val spotlightTokens: StateFlow<List<TokenDetails>?> = _spotlightTokens

    fun fetchSpotlightTokens(offset: Int, limit: Int) {
        viewModelScope.launch {
            val response = pref.getAccessToken()?.let { dashboardRepo.getSpotlightTokens(it, offset, limit) }
            _spotlightTokens.value = response?.body?.tokens
        }
    }
}