package com.example.tradingapp.data

data class TokenDetails(
    val _id: String,
    val name: String,
    val symbol: String,
    val description: String?,
    val decimals: Int,
    val icon: String,
    val tokenAddress: String,
    val marketCap: String,
    val tokenCreatedAt: String?,
    val isPartOfToken: Boolean,
    val isSpotlight: Boolean,
    val isHome: Boolean,
    val isActive: Boolean,
    val category: String,
    val createdAt: String,
    val updatedAt: String,
    val __v: Int,
    val holder: String,
    val price: String,
    val priceChange24h: String,
    val supply: String,
    val volume: Long,
    val graphType: String
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