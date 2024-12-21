package com.example.tradingapp.di.pref

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

    fun put(key: String, value: Int) {
        doEdit()
        editor?.putInt(key, value)
        doCommit()
    }

    fun put(key: String, value: Boolean) {
        doEdit()
        editor?.putBoolean(key, value)
        doCommit()
    }


    fun put(key: String, value: Float) {
        doEdit()
        editor?.putFloat(key, value)
        doCommit()
    }

    fun put(key: String, value: Double) {
        doEdit()
        editor?.putString(key, value.toString())
        doCommit()
    }


    fun put(key: String, value: Long) {
        doEdit()
        editor?.putLong(key, value)
        doCommit()
    }

    fun put(key: String, value: MutableSet<String>) {
        doEdit()
        editor?.putStringSet(key, value)
        doCommit()
    }

    fun getSet(key: String): MutableSet<String> {
        return pref.getStringSet(key, null) ?: mutableSetOf()
    }

    fun getString(key: String, defaultValue: String): String? {
        return pref.getString(key, defaultValue)
    }

    fun getString(key: String): String? {
        return pref.getString(key, null)
    }

    fun getStringWithoutNull(key: String): String {
        return pref.getString(key, "") ?: ""
    }


    fun getInt(key: String): Int {
        return pref.getInt(key, 0)
    }

    fun getInt(key: String, defaultValue: Int): Int {
        return pref.getInt(key, defaultValue)
    }

    fun getLong(key: String): Long {
        return pref.getLong(key, 0)
    }

    fun getLong(key: String, defaultValue: Long): Long {
        return pref.getLong(key, defaultValue)
    }

    fun getFloat(key: String): Float {
        return pref.getFloat(key, 0f)
    }

    fun getFloat(key: String, defaultValue: Float): Float {
        return pref.getFloat(key, defaultValue)
    }

    fun putArrayMap(key: String, value: ArrayMap<String, SparseIntArray>) {
        val stringValue = Gson().toJson(value)
        doEdit()
        editor?.putString(key, stringValue)
        doCommit()
    }

    fun getArrayMap(key: String): ArrayMap<String, SparseIntArray>? {
        val sparseArrayString = pref.getString(key, null) ?: return null
        val sparseArrayType = object : TypeToken<ArrayMap<String, SparseIntArray>>() {}.type
        return Gson().fromJson(sparseArrayString, sparseArrayType)
    }


    @JvmOverloads
    fun getDouble(key: String, defaultValue: Double = 0.0): Double {
        return try {
            java.lang.Double.valueOf(pref.getString(key, defaultValue.toString()) ?: "")
        } catch (nfe: NumberFormatException) {
            defaultValue
        }
    }

    fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return pref.getBoolean(key, defaultValue)
    }

    fun getBoolean(key: String): Boolean {
        return pref.getBoolean(key, false)
    }

    fun remove(vararg keys: String) {
        doEdit()
        for (key in keys) {
            editor?.remove(key)
        }
        doCommit()
    }


    /**
     * Save ArrayList in SharedPreference
     */

    fun saveArrayList(key: String, list: ArrayList<*>?) {
        val editor = pref.edit()
        val gson = Gson()
        val json = gson.toJson(list)
        editor.putString(key, json)
        editor.apply()
    }

    /**
     * Get ArrayList in SharedPreference
     */
    fun getArrayList(key: String): ArrayList<*> {
        val gson = Gson()
        val json = pref.getString(key, null)
        val type = object : TypeToken<ArrayList<*>>() {}.type
        return if (json.isNullOrEmpty()) {
            arrayListOf<Any>()
        } else {
            gson.fromJson(json, type)
        }
    }


    @SuppressLint("CommitPrefEdits")
    fun edit() {
        editor = pref.edit()
    }

    fun commit() {
        editor?.commit()
        editor = null
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

    fun loginUser(
        userId: String,
        token: String,
        email: String,
        companyId: String
    ) {
        put(KEY_LOGIN_TOKEN, token)
        put(KEY_LOGIN_ID, userId)
        put(KEY_LOGIN_COMPANY_ID, companyId)
        put(KEY_LOGIN_EMAIL, email)
    }

    fun putTimer(key: String, value: Int) {
        doEdit()
        editor?.putInt(key, value)
        doCommit()
    }

    fun getTimer(key: String, value: Int): Int {
        return pref.getInt(key, value)
    }

    fun setAutoLogStartTime(key: String, value: Long) {
        doEdit()
        editor?.putLong(key, value)
        doCommit()
    }

    fun getAutoLogStartTime(key: String): Long {
        return pref.getLong(key, 0)
    }

    fun setAutoLogStartDate(key: String, value: Long) {
        doEdit()
        editor?.putLong(key, value)
        doCommit()
    }

    fun getAutoLogStartDate(key: String): Long {
        return pref.getLong(key, 0)
    }

    fun setAutoLogId(key: String, value: String) {
        doEdit()
        editor?.putString(key, value)
        doCommit()
    }

    fun getAutoLogId(key: String): String? {
        return pref.getString(key, "")
    }

    fun getAccessToken() = getString(KEY_LOGIN_TOKEN)
    fun getEmail() = getString(KEY_LOGIN_EMAIL)
    fun getUserId() = getString(KEY_LOGIN_ID)

    fun getCompanyUserId() = getString(KEY_LOGIN_COMPANY_ID)

    fun clearSession() {
        clear()
    }

    fun clear() {
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
        const val API_LOGIN_NAME_PARAM = "loginName"
        const val SHAR_IS_URL_CHANGE = "false"
        const val KEY_LOGIN_NUMBER = "user_login_number"
        const val KEY_LOGIN_COMPANY_ID = "user_login_company_id"
        const val KEY_MENU_ITEMS = "menu_items"
        const val CONST_ARRAYLIST = "arrayList"
        const val CONST_SELECTED_URL = "selectedURL"

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