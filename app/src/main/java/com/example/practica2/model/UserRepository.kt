package com.example.practica2.model

// Modelo de usuario (Asegúrate de tener esta clase)

// Interfaz para Retrofit
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UserRepository {

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    // Configuración de Retrofit
    companion object {
        private const val BASE_URL = "http://10.0.2.2:3000/" // Para emulador, usa esta IP en lugar de localhost

        val apiService: ApiService by lazy {
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)
        }
    }

    fun registerUser(email: String, password: String, nombre: String, callback: (Boolean, String?) -> Unit) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val uid = auth.currentUser?.uid ?: ""
                val user = User(uid, email, nombre, "usuario")
                db.collection("usuarios").document(uid).set(user)
                    .addOnSuccessListener { callback(true, null) }
                    .addOnFailureListener { callback(false, it.message) }
            } else {
                callback(false, task.exception?.message)
            }
        }
    }

    fun loginUser(email: String, password: String, callback: (Boolean, String?) -> Unit) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                callback(true, null)
            } else {
                callback(false, task.exception?.message)
            }
        }
    }

    fun getUserRole(uid: String, callback: (String?) -> Unit) {
        db.collection("usuarios").document(uid).get().addOnSuccessListener { document ->
            if (document.exists()) {
                callback(document.getString("rol"))
            } else {
                callback(null)
            }
        }.addOnFailureListener {
            callback(null)
        }
    }
}
