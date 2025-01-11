package com.example.degn.repo

import com.example.degn.api.ApiService
import com.example.degn.data.MoonPaySignatureResponse
import com.example.degn.data.SellRequest
import com.example.degn.data.SellResponse
import com.example.degn.data.TransactionRequest
import com.example.degn.data.TransactionResponse
import com.example.degn.data.TransactionsResponse

class TransactionRepo(private val apiService: ApiService,private val mainRepo: MainRepo) {
    suspend fun getMoonPayBuySignature(token: String, url: String): MoonPaySignatureResponse? {
        val response = apiService.getMoonPayBuySignature("Bearer $token", url)
        return if (response.isSuccessful) {
            response.body()
        } else {
            if(mainRepo.handleApiError(response)) return null
            null
        }
    }

    suspend fun getMoonPaySellSignature(token: String, url: String): MoonPaySignatureResponse? {
        val response = apiService.getMoonPaySellSignature("Bearer $token", url)
        return if (response.isSuccessful) {
            response.body()
        } else {
            if(mainRepo.handleApiError(response)) return null
            null
        }
    }

    suspend fun getTransactions(token: String, offset: Int, limit: Int): TransactionsResponse? {
        val response = apiService.getTransactions("Bearer $token", offset, limit)
        return if (response.isSuccessful) {
            response.body()
        } else {
            if(mainRepo.handleApiError(response)) return null
            null
        }
    }

    suspend fun buyTransaction(token: String, request: TransactionRequest): TransactionResponse? {
        val response = apiService.buyTransaction("Bearer $token", request)
        return if (response.isSuccessful) {
            response.body()
        } else {
            if(mainRepo.handleApiError(response)) return null
            null
        }
    }

    suspend fun sellTransaction(token: String, request: SellRequest): SellResponse? {
        val response = apiService.sellTransaction("Bearer $token", request)
        return if (response.isSuccessful) {
            response.body()
        } else {
            if(mainRepo.handleApiError(response)) return null
            null
        }
    }
}