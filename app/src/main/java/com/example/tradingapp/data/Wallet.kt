package com.example.tradingapp.data

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