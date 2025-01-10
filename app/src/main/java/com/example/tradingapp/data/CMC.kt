package com.example.tradingapp.data

data class CoinMarketCapResponse(
    val status: StatusCMC,
    val data: Map<String, CryptoData>
)

data class StatusCMC(
    val timestamp: String,
    val error_code: Int,
    val error_message: String?,
    val elapsed: Int,
    val credit_count: Int
)

data class CryptoData(
    val id: Int,
    val name: String,
    val symbol: String,
    val slug: String,
    val num_market_pairs: Int,
    val date_added: String,
    val tags: List<String>,
    val max_supply: Double?,
    val circulating_supply: Double,
    val total_supply: Double,
    val platform: Platform?,
    val is_active: Int,
    val cmc_rank: Int,
    val quote: Quote
)

data class Platform(
    val id: Int,
    val name: String,
    val symbol: String,
    val slug: String,
    val token_address: String
)

data class Quote(
    val USD: USD
)

data class USD(
    val price: Double,
    val volume_24h: Double,
    val percent_change_1h: Double,
    val percent_change_24h: Double,
    val percent_change_7d: Double,
    val market_cap: Double,
    val last_updated: String
)
