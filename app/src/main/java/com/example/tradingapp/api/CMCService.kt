package com.example.tradingapp.api

import com.example.tradingapp.data.CoinMarketCapResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface CMCService {
    @GET("cryptocurrency/quotes/latest")
    suspend fun getCryptoQuote(
        @Query("symbol") symbol: String,
        @Query("convert") convert: String = "USD",
        @Header("X-CMC_PRO_API_KEY") apiKey: String
    ): CoinMarketCapResponse
}