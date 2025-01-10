package com.example.tradingapp.repo

import com.example.tradingapp.api.CMCService
import com.example.tradingapp.data.CoinMarketCapResponse

class CMCRepo(
    private val cmcService: CMCService
) {
    suspend fun getCryptoQuote(symbol: String): CoinMarketCapResponse {
        return cmcService.getCryptoQuote(
            symbol = symbol,
            apiKey = "65af7cb9-7405-4578-8b5b-304e65b2b672"
        )
    }
}