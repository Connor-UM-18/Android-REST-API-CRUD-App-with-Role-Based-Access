package com.example.practica2.model

data class User(
    val uid: String? = null, // uid puede ser nulo si no se recibe del backend directamente
    val email: String = "",
    val nombre: String = "",
    val fotoPerfil: String = "",
    val rol: String = "usuario" // O un valor por defecto apropiado
)