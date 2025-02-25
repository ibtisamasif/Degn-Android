package com.example.degn.viewModels.authentication

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.degn.data.ConnectAccountResponse
import com.example.degn.di.pref.DegnSharedPref
import com.example.degn.di.pref.DegnSharedPref.Companion.KEY_LOGIN_EMAIL
import com.example.degn.di.pref.DegnSharedPref.Companion.KEY_LOGIN_TOKEN
import com.example.degn.repo.AuthenticationRepo
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthenticationViewModel(
    private val authenticationRepo: AuthenticationRepo,
    private val pref: DegnSharedPref
) : ViewModel() {
    private val _connectResponse = MutableStateFlow<ConnectAccountResponse?>(null)
    var email = mutableStateOf("")
    var otp = mutableStateOf("")
    var isTimerRunning = mutableStateOf(false)
    var remainingTime = mutableIntStateOf(0)
    var startTimer: Boolean = true

    private val _error = MutableStateFlow<String?>(null)

    fun isUserLogin(): Boolean{
        return !pref.getAccessToken().isNullOrEmpty()
    }
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

    fun startTimer() {
        if (!isTimerRunning.value) {
            remainingTime.intValue = 60
            isTimerRunning.value = true

            viewModelScope.launch {
                while (remainingTime.intValue > 0) {
                    delay(1000L)
                    remainingTime.intValue--
                }
                startTimer = false
                isTimerRunning.value = false
            }
        }
    }


    fun verifyAccount(onSuccess: (Boolean)-> Unit) {
        viewModelScope.launch {
            try {
                val response = authenticationRepo.verifyAccount(email.value, otp.value)
                if(response.status.code == 201){
                    pref.put(KEY_LOGIN_TOKEN,response.body.token)
                    pref.put(KEY_LOGIN_EMAIL, email.value)
                    onSuccess.invoke(true)
                }
                else onSuccess.invoke(false)
            } catch (e: Exception) {
                 onSuccess.invoke(false)
                _error.value = e.message
            }
        }
    }

    fun resendOtp(){
        viewModelScope.launch {
            try {
                authenticationRepo.resendOtp(email.value)
            }catch (e: Exception){
                _error.value = e.message
            }
        }
    }
}