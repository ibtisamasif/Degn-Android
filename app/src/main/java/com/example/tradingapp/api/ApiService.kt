package com.example.tradingapp.api

import com.example.tradingapp.data.ConnectAccountRequest
import com.example.tradingapp.data.ConnectAccountResponse
import com.example.tradingapp.data.Status
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @POST("account/connect")
    suspend fun connectAccount(
        @Body request: ConnectAccountRequest
    ): ConnectAccountResponse

    @POST("account/verify")
    suspend fun verifyAccount(
        @Body request: Map<String, String>
    ): ConnectAccountResponse

    @GET("account/health")
    suspend fun checkHealth(): Status
}