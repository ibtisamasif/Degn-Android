package com.example.degn.repo

import com.example.degn.api.ApiService
import com.example.degn.data.ConnectAccountRequest
import com.example.degn.data.ConnectAccountResponse
import com.example.degn.data.ResendOtp

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