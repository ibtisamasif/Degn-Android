package com.example.tradingapp.repo

import com.example.tradingapp.api.ApiService
import com.example.tradingapp.data.GetProfileResponse

class ProfileRepo(private val apiService: ApiService) {
    suspend fun fetchUserProfile(token: String): GetProfileResponse? {
        val response = apiService.getProfile("Bearer $token")
        return if (response.isSuccessful) {
            response.body()
        } else {
            null
        }
    }
}