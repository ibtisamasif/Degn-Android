package com.example.tradingapp.viewModels.export

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tradingapp.di.pref.DegnSharedPref
import com.example.tradingapp.di.pref.DegnSharedPref.Companion.WALLET_KEY
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
    val walletKey = pref.getString(WALLET_KEY).toString()

    private val _secretPhrase = MutableStateFlow<List<String>?>(null)
    val secretPhrase: StateFlow<List<String>?> = _secretPhrase
    var otp = mutableStateOf("")
    var isShowSecretKey = mutableStateOf(false)

    fun sendOtp() {
        viewModelScope.launch {
            val response = pref.getAccessToken()?.let { walletRepo.sendWalletOtp(it)}
            _otpStatus.value = response?.status?.message
        }
    }

    fun verifyOtp(onSuccess: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                val response = pref.getAccessToken()?.let { walletRepo.verifyWalletOtp(it, otp.value) }
                _secretPhrase.value = response?.body?.secretPhrase
                onSuccess.invoke(true)
            } catch (e: Exception) {
                onSuccess.invoke(false)
            }
        }
    }
}