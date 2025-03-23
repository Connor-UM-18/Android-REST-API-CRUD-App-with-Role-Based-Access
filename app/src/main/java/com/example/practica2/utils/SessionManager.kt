package com.example.practica2.utils

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("UserSession", Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = sharedPreferences.edit()

    fun saveUserData(email: String, token: String) {
        editor.putString("USER_EMAIL", email)
        editor.putString("USER_TOKEN", token)
        editor.apply()
    }

    fun getUserEmail(): String? {
        return sharedPreferences.getString("USER_EMAIL", null)
    }

    fun getUserToken(): String? {
        return sharedPreferences.getString("USER_TOKEN", null)
    }

    fun clearSession() {
        editor.clear()
        editor.apply()
    }
}