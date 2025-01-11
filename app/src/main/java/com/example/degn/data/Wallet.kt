package com.example.degn.data

data class VerifyOtpRequest(
    val otp: String
)

data class VerifyWalletResponse(
    val status: Status,
    val body: WalletBody
)

data class WalletBody(
    val secretPhrase: List<String>
)

data class OtpResponse(
    val status: Status,
    val body: Body
)

data class UserBalanceResponse(
    val status: Status,
    val body: BalanceBody
)

data class BalanceBody(
    val totalBalance: Double,
    val cashBalance: Double,
    val tokenAccounts: List<TokenAccount>
)

data class TokenAccount(
    val mintAddress: String,
    val balance: Double,
    val valueInUSD: Double
)