package com.example.tradingapp.api

import com.example.tradingapp.data.ConnectAccountRequest
import com.example.tradingapp.data.ConnectAccountResponse
import com.example.tradingapp.data.MoonPaySignatureResponse
import com.example.tradingapp.data.OtpResponse
import com.example.tradingapp.data.ProfileResponse
import com.example.tradingapp.data.ResendOtp
import com.example.tradingapp.data.Status
import com.example.tradingapp.data.TokenResponse
import com.example.tradingapp.data.TokensResponse
import com.example.tradingapp.data.TransactionRequest
import com.example.tradingapp.data.TransactionResponse
import com.example.tradingapp.data.TransactionsResponse
import com.example.tradingapp.data.UpdateProfileRequest
import com.example.tradingapp.data.VerifyOtpRequest
import com.example.tradingapp.data.VerifyWalletResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
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

    @GET("token/{id}")
    suspend fun getTokenById(
        @Path("id") id: String,
        @Header("Authorization") token: String
    ): Response<TokenResponse>

    @GET("token/spotlight")
    suspend fun getSpotlightTokens(
        @Header("Authorization") token: String,
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): Response<TokensResponse>

    @GET("token")
    suspend fun getTokens(
        @Header("Authorization") token: String,
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): Response<TokensResponse>

    @GET("user/me")
    suspend fun getProfile(
        @Header("Authorization") token: String
    ): Response<ProfileResponse>

    @PUT("user/me")
    suspend fun updateUserProfile(
        @Header("Authorization") token: String,
        @Body request: UpdateProfileRequest
    ): Response<ProfileResponse>

    @Multipart
    @PUT("user/profile")
    suspend fun updateProfilePicture(
        @Header("Authorization") token: String,
        @Part file: MultipartBody.Part
    ): Response<ProfileResponse>

    @GET("user/export/wallet/otp")
    suspend fun sendWalletOtp(
        @Header("Authorization") token: String
    ): Response<OtpResponse>

    @POST("user/export/wallet/verify")
    suspend fun verifyWalletOtp(
        @Header("Authorization") token: String,
        @Body request: VerifyOtpRequest
    ): Response<VerifyWalletResponse>

    @GET("user/moonpay/buy/signature")
    suspend fun getMoonPayBuySignature(
        @Header("Authorization") token: String,
        @Query("url") url: String
    ): Response<MoonPaySignatureResponse>

    @GET("user/moonpay/sell/signature")
    suspend fun getMoonPaySellSignature(
        @Header("Authorization") token: String,
        @Query("url") url: String
    ): Response<MoonPaySignatureResponse>

    @GET("/transaction")
    suspend fun getTransactions(
        @Header("Authorization") token: String,
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): Response<TransactionsResponse>

    @POST("transaction/buy")
    suspend fun buyTransaction(
        @Header("Authorization") token: String,
        @Body request: TransactionRequest
    ): Response<TransactionResponse>

    @POST("transaction/sell")
    suspend fun sellTransaction(
        @Header("Authorization") token: String,
        @Body request: TransactionRequest
    ): Response<TransactionResponse>

}