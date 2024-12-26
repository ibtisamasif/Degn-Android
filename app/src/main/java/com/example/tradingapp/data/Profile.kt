package com.example.tradingapp.data

data class GetProfileResponse(
    val status: Status,
    val body: UserBody
)

data class UserBody(
    val user: User
)

data class User(
    val _id: String,
    val userName: String,
    val email: String,
    val role: String,
    val walletInfo: WalletInfo,
    val isEnableNotification: Boolean,
    val isActive: Boolean,
    val createdAt: String,
    val updatedAt: String,
    val __v: Int
)

data class WalletInfo(
    val turnKeyWalletId: String,
    val address: String
)