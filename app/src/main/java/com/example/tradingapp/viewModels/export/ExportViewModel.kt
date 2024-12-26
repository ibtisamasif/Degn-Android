package com.example.tradingapp.viewModels.export

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tradingapp.di.pref.DegnSharedPref
import com.example.tradingapp.repo.WalletRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ExportViewModel(
    private val walletRepo: WalletRepo,
    private val pref: DegnSharedPref
) : ViewModel() {

    private val _otpStatus = MutableStateFlow<String?>(null)
    val otpStatus: StateFlow<String?> = _otpStatus

    private val _secretPhrase = MutableStateFlow<List<String>?>(null)
    val secretPhrase: StateFlow<List<String>?> = _secretPhrase

    fun sendOtp(token: String) {
        viewModelScope.launch {
            val response = walletRepo.sendWalletOtp(token)
            _otpStatus.value = response?.status?.message
        }
    }

    fun verifyOtp(otp: String) {
        viewModelScope.launch {
            val response = pref.getAccessToken()?.let { walletRepo.verifyWalletOtp(it, otp) }
            _secretPhrase.value = response?.body?.secretPhrase
        }
    }
}