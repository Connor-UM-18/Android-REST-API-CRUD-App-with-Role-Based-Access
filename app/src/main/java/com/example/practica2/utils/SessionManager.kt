package com.example.practica2.utils

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {
<<<<<<< HEAD

=======
>>>>>>> 56d9615d32097a07c51d0e697c2172a2e8e7781e
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("UserSession", Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = sharedPreferences.edit()

<<<<<<< HEAD
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
=======
    fun saveUserData(email: String, token: String) {
        editor.putString("USER_EMAIL", email)
        editor.putString("USER_TOKEN", token)
        editor.apply()
    }

>>>>>>> 56d9615d32097a07c51d0e697c2172a2e8e7781e
    fun getUserEmail(): String? {
        return sharedPreferences.getString("USER_EMAIL", null)
    }

<<<<<<< HEAD
    // Función para obtener el token del usuario
=======
>>>>>>> 56d9615d32097a07c51d0e697c2172a2e8e7781e
    fun getUserToken(): String? {
        return sharedPreferences.getString("USER_TOKEN", null)
    }

<<<<<<< HEAD
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
=======
>>>>>>> 56d9615d32097a07c51d0e697c2172a2e8e7781e
    fun clearSession() {
        editor.clear()
        editor.apply()
    }
<<<<<<< HEAD

    // Función para cerrar la sesión del usuario
    fun logoutUser() {
        editor.clear() // Elimina todos los datos de la sesión
        editor.apply() // Aplica los cambios
    }
}
=======
}
>>>>>>> 56d9615d32097a07c51d0e697c2172a2e8e7781e
