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
    val marketCap: String,
    val tokenCreatedAt: String?,
    val isPartOfToken: Boolean,
    val createdAt: String,
    val updatedAt: String,
    val isSpotlight: Boolean,
    val isActive: Boolean,
    val __v: Int,
    val holder: String,
    val price: String,
    val priceChange24h: String,
    val supply: String,
    val volume: String
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