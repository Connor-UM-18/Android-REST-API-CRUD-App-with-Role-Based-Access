package com.example.practica2.model

import com.example.practica2.model.LoginRequest
import com.example.practica2.model.LoginResponse
import com.example.practica2.model.RegisterRequest
import com.example.practica2.model.RegisterResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

// Modelo de datos para la respuesta
//data class LoginRequest(val email: String, val password: String)
//data class LoginResponse(val token: String, val message: String)

interface ApiService {
    @POST("auth/login") // Ruta del backend
    fun login(@Body request: LoginRequest): Call<LoginResponse>
    @POST("auth/register") // Llamada al backend para registrar un usuario
    fun register(@Body request: RegisterRequest): Call<RegisterResponse>
}