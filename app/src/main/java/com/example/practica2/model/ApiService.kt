package com.example.practica2.model

import com.example.practica2.model.LoginRequest
import com.example.practica2.model.LoginResponse
import com.example.practica2.model.RegisterRequest
import com.example.practica2.model.RegisterResponse
import retrofit2.Call
import retrofit2.http.Body
<<<<<<< HEAD
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.*
=======
import retrofit2.http.POST
>>>>>>> 56d9615d32097a07c51d0e697c2172a2e8e7781e

// Modelo de datos para la respuesta
//data class LoginRequest(val email: String, val password: String)
//data class LoginResponse(val token: String, val message: String)

interface ApiService {
    @POST("auth/login") // Ruta del backend
    fun login(@Body request: LoginRequest): Call<LoginResponse>
<<<<<<< HEAD

    @POST("auth/register")
    fun register(@Body request: RegisterRequest): Call<RegisterResponse>
    @GET("/users/{email}")  // Ruta para obtener el perfil del usuario
    fun getUserProfile(@Path("email") email: String): Call<Profile>

    @PUT("/users/{userId}/profileImage")  // Ruta para actualizar la URL de la imagen
    fun updateUserProfile(
        @Path("userId") userId: String,
        @Field("nombre") nombre: String,
        @Field("email") email: String,
        @Field("fotoPerfil") fotoPerfil: String? //  Puede ser nulo si no se actualiza la foto
    ): Call<String>
=======
    @POST("auth/register") // Llamada al backend para registrar un usuario
    fun register(@Body request: RegisterRequest): Call<RegisterResponse>
>>>>>>> 56d9615d32097a07c51d0e697c2172a2e8e7781e
}