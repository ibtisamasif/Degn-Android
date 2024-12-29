package com.example.tradingapp.viewModels.profile

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tradingapp.data.UpdateProfileRequest
import com.example.tradingapp.data.User
import com.example.tradingapp.di.pref.DegnSharedPref
import com.example.tradingapp.di.pref.DegnSharedPref.Companion.CONST_IMAGE_URL
import com.example.tradingapp.di.pref.DegnSharedPref.Companion.JOINED_AT
import com.example.tradingapp.di.pref.DegnSharedPref.Companion.KEY_LOGIN_EMAIL
import com.example.tradingapp.di.pref.DegnSharedPref.Companion.KEY_LOGIN_NAME
import com.example.tradingapp.di.pref.DegnSharedPref.Companion.WALLET_KEY
import com.example.tradingapp.repo.ProfileRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class ProfileViewModel(
    private val profileRepo: ProfileRepo,
    private val pref: DegnSharedPref
) : ViewModel() {
    private val _userProfile = MutableStateFlow<User?>(null)
    var email = mutableStateOf("")
    var username = mutableStateOf("")
    var image = mutableStateOf("")
    val imageUri = mutableStateOf<Uri?>(null)
    val isLoading = mutableStateOf(true)
    var joinedAt = ""
    fun getProfile() {
        viewModelScope.launch {
            try {
                val localUsername = pref.getString(KEY_LOGIN_NAME) ?: ""
                val localEmail = pref.getString(KEY_LOGIN_EMAIL) ?: ""
                val localImageUrl = pref.getString(CONST_IMAGE_URL) ?: ""

                if (localUsername.isNotEmpty() && localEmail.isNotEmpty()) {
                    username.value = localUsername
                    email.value = localEmail
                    image.value = localImageUrl
                    joinedAt = pref.getString(JOINED_AT) ?: "Unknown"
                    isLoading.value = false
                } else {
                    val response = pref.getAccessToken()?.let { profileRepo.fetchUserProfile(it) }
                    _userProfile.value = response?.body?.user
                    email.value = response?.body?.user?.email.toString()
                    username.value = _userProfile.value?.userName.toString()
                    image.value = _userProfile.value?.profileUrl.toString()
                    val zonedDateTime = ZonedDateTime.parse(_userProfile.value?.createdAt)
                    val formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy")
                    joinedAt = zonedDateTime.format(formatter)
                    isLoading.value = false

                    pref.put(KEY_LOGIN_NAME, _userProfile.value?.userName.toString())
                    pref.put(KEY_LOGIN_EMAIL, _userProfile.value?.email.toString())
                    pref.put(WALLET_KEY, _userProfile.value?.walletInfo?.turnKeyWalletId.toString())
                    pref.put(CONST_IMAGE_URL, _userProfile.value?.profileUrl.toString())
                    pref.put(JOINED_AT, joinedAt)
                }
            } catch (e: Exception) {
                Log.e("error", e.message.toString())
                isLoading.value = false
            }
        }
    }


    fun updateProfilePicture(file: MultipartBody.Part) {
        viewModelScope.launch {
            try {
                val response = pref.getAccessToken()?.let { profileRepo.updateProfilePicture(it, file) }
            } catch (e: Exception) {
                Log.e("error", e.message.toString())
            }
        }
    }

    fun updateUserProfile(){
        viewModelScope.launch {
            try {
                val request = UpdateProfileRequest(userName = username.value, false)
                pref.getAccessToken()?.let { profileRepo.updateUserProfile(it,request) }
            }catch (e: Exception){

            }
        }
    }

    fun uriToFile(context: Context, uri: Uri): File? {
        val contentResolver: ContentResolver = context.contentResolver
        val fileName = "temp_image_${System.currentTimeMillis()}.jpg"
        val tempFile = File(context.cacheDir, fileName)

        try {
            val inputStream: InputStream? = contentResolver.openInputStream(uri)
            val outputStream = FileOutputStream(tempFile)
            inputStream?.copyTo(outputStream)
            inputStream?.close()
            outputStream.close()
            return tempFile
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }
}