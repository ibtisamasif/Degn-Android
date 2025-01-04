package com.example.tradingapp.repo

import com.example.tradingapp.api.ApiService
import com.example.tradingapp.data.ConnectAccountRequest
import com.example.tradingapp.data.ConnectAccountResponse
import com.example.tradingapp.data.ResendOtp
import com.example.tradingapp.data.Status

class AuthenticationRepo(private val apiService: ApiService)  {
    suspend fun connectAccount(email: String, referralCode: String?): ConnectAccountResponse {
        val request = ConnectAccountRequest(email, referralCode)
        return apiService.connectAccount(request)
    }

    suspend fun verifyAccount(email: String, otp: String): ConnectAccountResponse {
        val request = mapOf("email" to email, "otp" to otp)
        return apiService.verifyAccount(request)
    }

    suspend fun resendOtp(email: String): ConnectAccountResponse {
        val  request = ResendOtp(email= email)
        return apiService.resendOtp(request)
    }
}