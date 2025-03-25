package com.example.practica2.utils

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("UserSession", Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = sharedPreferences.edit()

    // Función para guardar los datos del usuario
    fun saveUserData(email: String, token: String, nombre: String? = null, fotoPerfil: String? = null, rol: String? = null, password: String? = null) {
        editor.putString("USER_EMAIL", email)
        editor.putString("USER_TOKEN", token)
        editor.putString("USER_NOMBRE", nombre)
        fotoPerfil?.let { editor.putString("USER_FOTO_PERFIL", fotoPerfil) }
        rol?.let { editor.putString("USER_ROL", rol) } // Guardando el rol
        password?.let { editor.putString("USER_PASSWORD", password) } // Guardando la contraseña
        editor.apply()
    }

    // Función para obtener el correo del usuario
    fun getUserEmail(): String? {
        return sharedPreferences.getString("USER_EMAIL", null)
    }

    // Función para obtener el token del usuario
    fun getUserToken(): String? {
        return sharedPreferences.getString("USER_TOKEN", null)
    }

    // Función para obtener el nombre del usuario
    fun getUserNombre(): String? {
        return sharedPreferences.getString("USER_NOMBRE", null)
    }

    // Función para obtener la foto del perfil del usuario
    fun getUserFotoPerfil(): String? {
        return sharedPreferences.getString("USER_FOTO_PERFIL", null)
    }

    // Nueva función para obtener el rol del usuario
    fun getUserRol(): String? {
        return sharedPreferences.getString("USER_ROL", null) // Obtener el rol del usuario
    }
    // Función para obtener la contraseña del usuario
    fun getUserPassword(): String? {
        return sharedPreferences.getString("USER_PASSWORD", null)
    }
    
    // Función para limpiar la sesión
    fun clearSession() {
        editor.clear()
        editor.apply()
    }

    // Función para cerrar la sesión del usuario
    fun logoutUser() {
        editor.clear() // Elimina todos los datos de la sesión
        editor.apply() // Aplica los cambios
    }
}
