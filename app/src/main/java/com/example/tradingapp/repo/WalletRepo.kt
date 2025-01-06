package com.example.tradingapp.repo

import com.example.tradingapp.api.ApiService
import com.example.tradingapp.data.OtpResponse
import com.example.tradingapp.data.UserBalanceResponse
import com.example.tradingapp.data.VerifyOtpRequest
import com.example.tradingapp.data.VerifyWalletResponse

class WalletRepo(private val apiService: ApiService) {

    suspend fun sendWalletOtp(token: String): OtpResponse? {
        val response = apiService.sendWalletOtp("Bearer $token")
        return if (response.isSuccessful) {
            response.body()
        } else {
            null
        }
    }

    suspend fun verifyWalletOtp(token: String, otp: String): VerifyWalletResponse? {
        val response = apiService.verifyWalletOtp("Bearer $token", VerifyOtpRequest(otp))
        return if (response.isSuccessful) {
            response.body()
        } else {
            null
        }
    }

    suspend fun getUserBalance(token: String): UserBalanceResponse? {
        val response = apiService.getUserBalance("Bearer $token")
        return if (response.isSuccessful) {
            response.body()
        } else {
            null
        }
    }

    suspend fun getUserTokenBalance(token: String, tokenId: String): UserBalanceResponse? {
        val response = apiService.getUserTokenBalance("Bearer $token",tokenId)
        return if (response.isSuccessful) {
            response.body()
        } else {
            null
        }
    }

}