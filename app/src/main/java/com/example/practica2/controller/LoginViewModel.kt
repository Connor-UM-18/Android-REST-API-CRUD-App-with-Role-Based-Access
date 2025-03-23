package com.example.practica2.controller

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.practica2.model.LoginRequest
import com.example.practica2.model.LoginResponse
import com.example.practica2.model.RetrofitClient
import com.example.practica2.utils.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    private val sessionManager = SessionManager(application.applicationContext)
    private val _loginResponse = MutableLiveData<String>()
    val loginResponse: LiveData<String> get() = _loginResponse

    fun login(email: String, password: String) {
        val request = LoginRequest(email, password)
        RetrofitClient.apiService.login(request).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    val userToken = response.body()?.token ?: ""
                    sessionManager.saveUserData(email, userToken) // Guardar en sesión
                    _loginResponse.value = "Login exitoso"
                } else {
                    _loginResponse.value = "Error en login: " + response.errorBody()?.string()
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                _loginResponse.value = "Error de conexión: ${t.message}"
            }
        })
    }
}