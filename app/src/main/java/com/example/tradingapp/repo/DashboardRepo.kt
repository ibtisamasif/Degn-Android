package com.example.tradingapp.repo

import com.example.tradingapp.api.ApiService
import com.example.tradingapp.data.TokenResponse
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

    suspend fun getTokens(token: String, offset: Int, limit: Int): TokensResponse? {
        val response = apiService.getTokens("Bearer $token", offset, limit)
        return if (response.isSuccessful) {
            response.body()
        } else {
            null
        }
    }

    suspend fun getTokenById(token: String,id: String): TokenResponse? {
        val response = apiService.getTokenById(id,"Bearer $token")
        return if (response.isSuccessful) {
            response.body()
        } else {
            null
        }
    }
}