package com.example.tradingapp.viewModels.authentication

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tradingapp.data.ConnectAccountResponse
import com.example.tradingapp.di.pref.DegnSharedPref
import com.example.tradingapp.repo.AuthenticationRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthenticationViewModel(
    private val authenticationRepo: AuthenticationRepo,
    private val pref: DegnSharedPref
) : ViewModel() {
    private val _connectResponse = MutableStateFlow<ConnectAccountResponse?>(null)
    val connectResponse: StateFlow<ConnectAccountResponse?> = _connectResponse
    var email = mutableStateOf("")
    var otp = mutableStateOf("")
    var showBiometric: Boolean = false

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun connectAccount(referralCode: String? = "",onSuccess: (Boolean)-> Unit) {
        viewModelScope.launch {
            try {
                val response = authenticationRepo.connectAccount(email.value, referralCode)
                _connectResponse.value = response
                if(response.status.code == 201) onSuccess.invoke(true)
                else onSuccess.invoke(false)

            } catch (e: Exception) {
                 onSuccess.invoke(false)
                _error.value = e.message
            }
        }
    }

    fun verifyAccount(email: String, otp: String) {
        viewModelScope.launch {
            try {
                authenticationRepo.verifyAccount(email, otp)
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }

    fun checkHealth() {
        viewModelScope.launch {
            try {
                authenticationRepo.checkHealth()
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }
}