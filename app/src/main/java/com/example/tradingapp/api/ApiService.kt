package com.example.tradingapp.api

import com.example.tradingapp.data.ConnectAccountRequest
import com.example.tradingapp.data.ConnectAccountResponse
import com.example.tradingapp.data.ResendOtp
import com.example.tradingapp.data.Status
import com.example.tradingapp.data.TokenDetails
import com.example.tradingapp.data.TokensResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

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

    @PUT("account/resend/otp")
    suspend fun resendOtp(
        @Body request: ResendOtp
    ): ConnectAccountResponse

    @GET("api/v1/token/{id}")
    suspend fun getTokenById(@Path("id") id: String): Response<TokenDetails>

    @GET("api/v1/token")
    suspend fun getTokens(): Response<TokensResponse>
}