package com.example.degn.viewModels.wallet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.degn.data.BalanceBody
import com.example.degn.di.pref.DegnSharedPref
import com.example.degn.di.pref.DegnSharedPref.Companion.CONST_IMAGE_URL
import com.example.degn.di.pref.DegnSharedPref.Companion.KEY_LOGIN_NAME
import com.example.degn.repo.WalletRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WalletViewModel(
    private val walletRepo: WalletRepo,
    private val pref: DegnSharedPref
): ViewModel() {

    var userName = pref.getString(KEY_LOGIN_NAME)
    var profileImage = pref.getString(CONST_IMAGE_URL)

    private val _userBalance = MutableStateFlow<BalanceBody?>(null)
    val userBalance: StateFlow<BalanceBody?> = _userBalance

    fun fetchUserBalance() {
        viewModelScope.launch {
            val response = pref.getAccessToken()?.let { walletRepo.getUserBalance(it)}
            _userBalance.value = response?.body
        }
    }

    fun getUserDetail(){
        userName = pref.getString(KEY_LOGIN_NAME)
        profileImage = pref.getString(CONST_IMAGE_URL)
    }

    fun setAmount(amount: String){
        pref.put("Amount",amount);
    }
}