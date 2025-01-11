package com.example.degn.data

data class ProfileResponse(
    val status: Status,
    val body: UserBody
)

data class UpdateProfileRequest(
    val userName: String,
    val isEnableNotification: Boolean
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
    val __v: Int,
    val profileUrl: String,
    val referralCode: String,
)

data class WalletInfo(
    val turnKeyWalletId: String,
    val address: String,
    val hasExported: Boolean
)