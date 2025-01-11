package com.example.degn.repo

import com.example.degn.api.ApiService
import com.example.degn.data.OtpResponse
import com.example.degn.data.UserBalanceResponse
import com.example.degn.data.VerifyOtpRequest
import com.example.degn.data.VerifyWalletResponse

class WalletRepo(private val apiService: ApiService,private val mainRepo: MainRepo) {

    suspend fun sendWalletOtp(token: String): OtpResponse? {
        val response = apiService.sendWalletOtp("Bearer $token")
        return if (response.isSuccessful) {
            response.body()
        } else {
            if(mainRepo.handleApiError(response)) return null
            null
        }
    }

    suspend fun verifyWalletOtp(token: String, otp: String): VerifyWalletResponse? {
        val response = apiService.verifyWalletOtp("Bearer $token", VerifyOtpRequest(otp))
        return if (response.isSuccessful) {
            response.body()
        } else {
            if(mainRepo.handleApiError(response)) return null
            null
        }
    }

    suspend fun getUserBalance(token: String): UserBalanceResponse? {
        val response = apiService.getUserBalance("Bearer $token")
        return if (response.isSuccessful) {
            response.body()
        } else {
            if(mainRepo.handleApiError(response)) return null
            null
        }
    }

    suspend fun getUserTokenBalance(token: String, tokenId: String): UserBalanceResponse? {
        val response = apiService.getUserTokenBalance("Bearer $token",tokenId)
        return if (response.isSuccessful) {
            response.body()
        } else {
            if(mainRepo.handleApiError(response)) return null
            null
        }
    }

}