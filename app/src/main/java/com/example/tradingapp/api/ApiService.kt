package com.example.tradingapp.api

import com.example.tradingapp.data.ConnectAccountRequest
import com.example.tradingapp.data.ConnectAccountResponse
import com.example.tradingapp.data.GetProfileResponse
import com.example.tradingapp.data.OtpResponse
import com.example.tradingapp.data.ResendOtp
import com.example.tradingapp.data.Status
import com.example.tradingapp.data.TokenDetails
import com.example.tradingapp.data.TokensResponse
import com.example.tradingapp.data.VerifyOtpRequest
import com.example.tradingapp.data.VerifyWalletResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

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
    suspend fun getTokenById(
        @Path("id") id: String,
        @Header("Authorization") token: String
    ): Response<TokenDetails>

    @GET("api/v1/token/spotlight")
    suspend fun getSpotlightTokens(
        @Header("Authorization") token: String,
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): Response<TokensResponse>

    @GET("api/v1/token")
    suspend fun getTokens(
        @Header("Authorization") token: String,
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): Response<TokensResponse>

    @GET("api/v1/user/me")
    suspend fun getProfile(
        @Header("Authorization") token: String
    ): Response<GetProfileResponse>

    @GET("api/v1/user/export/wallet/otp")
    suspend fun sendWalletOtp(
        @Header("Authorization") token: String
    ): Response<OtpResponse>

    @POST("api/v1/user/export/wallet/verify")
    suspend fun verifyWalletOtp(
        @Header("Authorization") token: String,
        @Body request: VerifyOtpRequest
    ): Response<VerifyWalletResponse>

}