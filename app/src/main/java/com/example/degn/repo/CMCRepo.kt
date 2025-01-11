package com.example.degn.repo

import com.example.degn.api.CMCService
import com.example.degn.data.CoinMarketCapResponse

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