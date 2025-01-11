package com.example.degn.api

import com.example.degn.data.QuoteResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface QuoteApiService {
    @GET("quote")
    suspend fun getQuote(
        @Query("inputMint") inputMint: String,
        @Query("outputMint") outputMint: String,
        @Query("amount") amount: Long,
        @Query("slippageBps") slippageBps: Int
    ): Response<QuoteResponse>
}