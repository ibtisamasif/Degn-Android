package com.example.tradingapp.repo

import com.example.tradingapp.api.ApiService
import com.example.tradingapp.data.TokensResponse

class DashboardRepo(private val apiService: ApiService) {
    suspend fun getSpotlightTokens(token: String, offset: Int, limit: Int): TokensResponse? {
        val response = apiService.getSpotlightTokens("Bearer $token", offset, limit)
        return if (response.isSuccessful) {
            response.body()
        } else {
            null
        }
    }
}