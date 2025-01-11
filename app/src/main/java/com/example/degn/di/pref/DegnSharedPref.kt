package com.example.degn.di.pref

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.util.ArrayMap
import android.util.SparseIntArray
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class DegnSharedPref(val pref: SharedPreferences) {


    private var editor: SharedPreferences.Editor? = null

    fun put(key: String, value: String) {
        doEdit()
        editor?.putString(key, value)
        doCommit()
    }

    fun getString(key: String): String? {
        return pref.getString(key, null)
    }

    fun remove(vararg keys: String) {
        doEdit()
        for (key in keys) {
            editor?.remove(key)
        }
        doCommit()
    }

    @SuppressLint("CommitPrefEdits")
    private fun doEdit() {
        if (editor == null) {
            editor = pref.edit()
        }
    }

    private fun doCommit() {
        if (editor != null) {
            editor?.commit()
            editor = null
        }
    }

    fun getAccessToken() = getString(KEY_LOGIN_TOKEN)

    fun clearSession() {
        clear()
    }

    private fun clear() {
        doEdit()
        remove(KEY_LOGIN_ID)
        remove(KEY_LOGIN_ROLE)
        remove(KEY_LOGIN_NAME)
        remove(KEY_LOGIN_NUMBER)
        remove(KEY_LOGIN_EMAIL)
        remove(KEY_LOGIN_TOKEN)
        doCommit()
    }

    companion object {
        const val KEY_LOGIN_ID = "user_login_id"
        const val KEY_LOGIN_TOKEN = "user_login_token"
        const val KEY_LOGIN_EMAIL = "user_login_email"
        const val KEY_LOGIN_ROLE = "user_login_role"
        const val KEY_LOGIN_NAME = "user_login_name"
        const val WALLET_KEY = "user_wallet_key"
        const val KEY_LOGIN_NUMBER = "user_login_number"
        const val CONST_IMAGE_URL = "imageURL"
        const val REFERRAL_CODE = "referralCode"
        const val JOINED_AT = "joinedDate"

        // Sharedpref file name
        const val PREF_NAME = "ProjectDegnPrefs"

        fun getInstance(context: Context): DegnSharedPref{
            return DegnSharedPref(
                pref = context.getSharedPreferences(
                    PREF_NAME,
                    Context.MODE_PRIVATE
                )
            )
        }
    }
}