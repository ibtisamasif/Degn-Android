package com.example.tradingapp.repo

import com.example.tradingapp.api.ApiService
import com.example.tradingapp.api.QuoteApiService
import com.example.tradingapp.data.QuoteResponse
import com.example.tradingapp.data.TokenResponse
import com.example.tradingapp.data.TokensResponse
import com.example.tradingapp.utils.Constants.Companion.INPUT_MINT

class DashboardRepo(
    private val apiService: ApiService,
    private val quoteApiService: QuoteApiService,
    private val mainRepo: MainRepo
) {
    suspend fun getSpotlightTokens(token: String, offset: Int, limit: Int): TokensResponse? {
        val response = apiService.getSpotlightTokens("Bearer $token", offset, limit)
        return if (response.isSuccessful) {
            response.body()
        } else {
            if (mainRepo.handleApiError(response)) return null
            null
        }
    }

    suspend fun getTokens(token: String, offset: Int, limit: Int): TokensResponse? {
        val response = apiService.getTokens("Bearer $token", offset, limit)
        return if (response.isSuccessful) {
            response.body()
        } else {
            if (mainRepo.handleApiError(response)) return null
            null
        }
    }

    suspend fun getTokenById(token: String, id: String): TokenResponse? {
        val response = apiService.getTokenById(id, "Bearer $token")
        return if (response.isSuccessful) {
            response.body()
        } else {
            if (mainRepo.handleApiError(response)) return null
            null
        }
    }

    suspend fun getQuote(
        outputMint: String,
        amount: Long,
    ): QuoteResponse? {
        return try {
            val response = quoteApiService.getQuote(INPUT_MINT, outputMint, amount, 1000)
            if (response.isSuccessful) {
                response.body()
            } else {
                throw Exception("Error: ${response.errorBody()?.string()}")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}