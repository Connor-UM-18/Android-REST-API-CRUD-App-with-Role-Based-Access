package com.example.practica2.model

data class RegisterRequest(
    val email: String,
    val password: String,
    val nombre: String
)