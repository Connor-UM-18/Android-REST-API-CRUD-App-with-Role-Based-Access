package com.example.practica2.controller // Asegúrate de que este paquete sea correcto

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.practica2.model.RegisterRequest
import com.example.practica2.model.RegisterResponse
import com.example.practica2.model.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel : ViewModel() {
    private val _registerResponse = MutableLiveData<String>()
    val registerResponse: LiveData<String> get() = _registerResponse

    fun register(email: String, password: String, nombre: String) {
        val request = RegisterRequest(email, password, nombre)
        RetrofitClient.apiService.register(request).enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                if (response.isSuccessful) {
                    _registerResponse.value = "Registro exitoso: " + (response.body()?.message ?: "Token no disponible") // Manejar nulo
                } else {
                    _registerResponse.value = "Error en registro: " + (response.errorBody()?.string() ?: "Error desconocido") // Manejar nulo
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                _registerResponse.value = "Error de conexión: ${t.message}"
            }
        })
    }
}