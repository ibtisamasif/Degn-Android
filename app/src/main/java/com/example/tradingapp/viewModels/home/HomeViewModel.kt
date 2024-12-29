package com.example.tradingapp.viewModels.home

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
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
    val isLoading = mutableStateOf(true)
    private val _spotlightTokens = MutableStateFlow<List<TokenDetails>?>(null)
    val spotlightTokens: StateFlow<List<TokenDetails>?> = _spotlightTokens

    private val _tokens = MutableStateFlow<List<TokenDetails>?>(null)
    val tokens: StateFlow<List<TokenDetails>?> = _tokens

    private val _token = MutableStateFlow<TokenDetails?>(null)
    val token: StateFlow<TokenDetails?> = _token

    var isTokensLoaded = false

    val offset = mutableIntStateOf(0)
    val selectedCoinId = mutableStateOf("")
    private val limit = 10

    private var allTokensLoaded = mutableStateOf(false)
    var newItemLoaded = mutableStateOf(false)

    fun fetchTokens() {
        if (allTokensLoaded.value) return

        isLoading.value = true

        viewModelScope.launch {
            val response = pref.getAccessToken()?.let {
                dashboardRepo.getTokens(it, offset.intValue, limit)
            }
            val newTokens = response?.body?.tokens ?: emptyList()
            _tokens.value = if(_tokens.value == null) newTokens else _tokens.value!! + newTokens
            if (newTokens.isEmpty()) {
                allTokensLoaded.value = true
            }
            isLoading.value = false
            newItemLoaded.value = true
        }
    }

    fun fetchSpotlightTokens() {
        viewModelScope.launch {
            val response = pref.getAccessToken()?.let { dashboardRepo.getSpotlightTokens(it, offset.intValue, limit) }
            _spotlightTokens.value = response?.body?.tokens
        }
    }

    suspend fun fetchTokenByID(id: String){
        val response = pref.getAccessToken()?.let { dashboardRepo.getTokenById(it, id) }
        _token.value = response?.body?.token
        isLoading.value = false
    }
}