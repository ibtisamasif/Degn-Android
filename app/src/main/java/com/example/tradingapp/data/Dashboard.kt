package com.example.tradingapp.data

data class TokenDetails(
    val _id: String,
    val name: String,
    val description: String?,
    val symbol: String,
    val uri: String,
    val decimals: Int,
    val image: String,
    val tokenAddress: String,
    val marketCap: MarketCap,
    val tokenCreatedAt: String,
    val isPartOfToken: Boolean,
    val createdAt: String,
    val updatedAt: String,
    val isSpotlight: Boolean,
    val isActive: Boolean,
    val __v: Int
)

data class MarketCap(
    val quote: String,
    val usd: String
)

data class TokensResponse(
    val status: Status,
    val body: TokensBody
)

data class TokenResponse(
    val status: Status,
    val body: TokenBody
)

data class TokensBody(
    val tokens: List<TokenDetails>
)

data class TokenBody(
    val token: TokenDetails
)