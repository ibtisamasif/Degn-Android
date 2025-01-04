package com.example.tradingapp.data

data class QuoteResponse(
    val inputMint: String,
    val inAmount: String,
    val outputMint: String,
    val outAmount: String,
    val otherAmountThreshold: String,
    val swapMode: String,
    val slippageBps: Int,
    val platformFee: Any?, // Nullable since it can be null
    val priceImpactPct: String,
    val routePlan: List<RoutePlan>,
    val scoreReport: Any?, // Nullable since it can be null
    val contextSlot: Long,
    val timeTaken: Double
)

data class RoutePlan(
    val swapInfo: SwapInfo,
    val percent: Int
)

data class SwapInfo(
    val ammKey: String,
    val label: String,
    val inputMint: String,
    val outputMint: String,
    val inAmount: String,
    val outAmount: String,
    val feeAmount: String,
    val feeMint: String
)
