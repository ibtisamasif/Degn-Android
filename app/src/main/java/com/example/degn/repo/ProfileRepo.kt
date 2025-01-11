package com.example.degn.repo

import com.example.degn.api.ApiService
import com.example.degn.data.ProfileResponse
import com.example.degn.data.UpdateProfileRequest
import okhttp3.MultipartBody

class ProfileRepo(private val apiService: ApiService,private val mainRepo: MainRepo) {
    suspend fun fetchUserProfile(token: String): ProfileResponse? {
        val response = apiService.getProfile("Bearer $token")
        return if (response.isSuccessful) {
            response.body()
        } else {
            if(mainRepo.handleApiError(response)) return null
            null
        }
    }

    suspend fun updateUserProfile(token: String, request: UpdateProfileRequest): ProfileResponse? {
        val response = apiService.updateUserProfile("Bearer $token", request)
        return if (response.isSuccessful) {
            response.body()
        } else {
            if(mainRepo.handleApiError(response)) return null
            null
        }
    }

    suspend fun updateProfilePicture(token: String, file: MultipartBody.Part): ProfileResponse? {
        val response = apiService.updateProfilePicture("Bearer $token", file)
        return if (response.isSuccessful) {
            response.body()
        } else {
            if(mainRepo.handleApiError(response)) return null
            null
        }
    }

    suspend fun deleteUserAccount(token: String): Boolean {
        return try {
            val response = apiService.deleteUserAccount(token)
            if (mainRepo.handleApiError(response)) return false
            response.isSuccessful
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }


}