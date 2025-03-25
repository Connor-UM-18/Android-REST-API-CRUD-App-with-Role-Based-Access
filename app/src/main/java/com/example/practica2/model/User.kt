package com.example.practica2.model

data class User(
<<<<<<< HEAD
    val uid: String? = null, // uid puede ser nulo si no se recibe del backend directamente
    val email: String = "",
    val nombre: String = "",
    val fotoPerfil: String = "",
    val rol: String = "usuario" // O un valor por defecto apropiado
=======
    val uid: String = "",
    val email: String = "",
    val nombre: String = "",
    val rol: String = "usuario"
>>>>>>> 56d9615d32097a07c51d0e697c2172a2e8e7781e
)