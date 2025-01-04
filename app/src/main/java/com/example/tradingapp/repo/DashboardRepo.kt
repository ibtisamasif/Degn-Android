package com.example.tradingapp.repo

import com.example.tradingapp.api.ApiService
import com.example.tradingapp.api.QuoteApiService
import com.example.tradingapp.data.QuoteResponse
import com.example.tradingapp.data.TokenResponse
import com.example.tradingapp.data.TokensResponse
import com.example.tradingapp.utils.Constants.Companion.OUTPUT_MINT

class DashboardRepo(
    private val apiService: ApiService,
    private val quoteApiService: QuoteApiService
) {
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

    suspend fun getQuote(
        inputMint: String,
        amount: Long,
    ): QuoteResponse? {
        return try {
            val response = quoteApiService.getQuote(inputMint, OUTPUT_MINT, amount, 1000)
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