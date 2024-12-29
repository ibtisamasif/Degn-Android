package com.example.tradingapp.repo

import com.example.tradingapp.api.ApiService
import com.example.tradingapp.data.MoonPaySignatureResponse
import com.example.tradingapp.data.TransactionRequest
import com.example.tradingapp.data.TransactionResponse
import com.example.tradingapp.data.TransactionsResponse

class TransactionRepo(private val apiService: ApiService) {
    suspend fun getMoonPayBuySignature(token: String, url: String): MoonPaySignatureResponse? {
        val response = apiService.getMoonPayBuySignature("Bearer $token", url)
        return if (response.isSuccessful) {
            response.body()
        } else {
            null
        }
    }

    suspend fun getMoonPaySellSignature(token: String, url: String): MoonPaySignatureResponse? {
        val response = apiService.getMoonPaySellSignature("Bearer $token", url)
        return if (response.isSuccessful) {
            response.body()
        } else {
            null
        }
    }

    suspend fun getTransactions(token: String, offset: Int, limit: Int): TransactionsResponse? {
        val response = apiService.getTransactions("Bearer $token", offset, limit)
        return if (response.isSuccessful) {
            response.body()
        } else {
            null
        }
    }

    suspend fun buyTransaction(token: String, request: TransactionRequest): TransactionResponse? {
        val response = apiService.buyTransaction("Bearer $token", request)
        return if (response.isSuccessful) {
            response.body()
        } else {
            null
        }
    }

    suspend fun sellTransaction(token: String, request: TransactionRequest): TransactionResponse? {
        val response = apiService.sellTransaction("Bearer $token", request)
        return if (response.isSuccessful) {
            response.body()
        } else {
            null
        }
    }
}