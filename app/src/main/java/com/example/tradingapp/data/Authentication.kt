package com.example.tradingapp.data

data class ConnectAccountRequest(
    val email: String,
    val referralCode: String?
)

data class ResendOtp(
    val email: String,
)

data class ConnectAccountResponse(
    val status: Status,
    val body: Body
)

data class Status(
    val code: Int,
    val message: String
)

data class Body(
    val token: String
)
