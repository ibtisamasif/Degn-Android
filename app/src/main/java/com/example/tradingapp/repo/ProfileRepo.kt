package com.example.tradingapp.repo

import com.example.tradingapp.api.ApiService
import com.example.tradingapp.data.ProfileResponse
import com.example.tradingapp.data.UpdateProfileRequest
import okhttp3.MultipartBody

class ProfileRepo(private val apiService: ApiService) {
    suspend fun fetchUserProfile(token: String): ProfileResponse? {
        val response = apiService.getProfile("Bearer $token")
        return if (response.isSuccessful) {
            response.body()
        } else {
            null
        }
    }

    suspend fun updateUserProfile(token: String, request: UpdateProfileRequest): ProfileResponse? {
        val response = apiService.updateUserProfile("Bearer $token", request)
        return if (response.isSuccessful) {
            response.body()
        } else {
            null
        }
    }

    suspend fun updateProfilePicture(token: String, file: MultipartBody.Part): ProfileResponse? {
        val response = apiService.updateProfilePicture("Bearer $token", file)
        return if (response.isSuccessful) {
            response.body()
        } else {
            null
        }
    }

    suspend fun deleteUserAccount(token: String): Boolean {
        return try {
            val response = apiService.deleteUserAccount(token)
            response.isSuccessful
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

}