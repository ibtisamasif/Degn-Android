package com.example.degn.data

data class MoonPaySignatureResponse(
    val status: Status,
    val body: MoonPayBody
)

data class MoonPayBody(
    val signature: String
)

data class TransactionsResponse(
    val status: Status,
    val body: TransactionsBody
)

data class TransactionRequest(
    val amount: String,
    val token: String,
    val slippage: String,
)

data class SellRequest(
    val amount: String,
    val amountInUSDC: String,
    val token: String,
    val slippage: String,
)

data class SellResponse(
    val status: Status,
    val body: SellStatusBody
)

data class SellStatusBody(
    val status: String
)

data class TransactionResponse(
    val status: Status,
    val body: TransactionStatusBody
)

data class TransactionStatusBody(
    val status: TransactionStatus
)

data class TransactionStatus(
    val status: String
)

data class TransactionsBody(
    val tranactions: List<Transaction>
)

data class Transaction(
    val _id: String,
    val transactionHash: String?,
    val amount: String,
    val tokenAddress: String,
    val status: String,
    val fee: String?,
    val type: String,
    val user: String,
    val transaction: String?,
    val token: TokenDetails,
    val isActive: Boolean,
    val createdAt: String,
    val updatedAt: String,
    val __v: Int
)
