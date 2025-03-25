package com.example.practica2.controller

<<<<<<< HEAD
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practica2.model.Profile
import com.example.practica2.model.RetrofitClient
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class ProfileViewModel : ViewModel() {
    private val _profile = MutableLiveData<Profile?>()
    val profile: LiveData<Profile?> get() = _profile  // Change to LiveData

    init {
        _profile.value = null // Initialize with null
    }

    fun getUserProfile(email: String) {
        viewModelScope.launch {
            RetrofitClient.apiService.getUserProfile(email).enqueue(object : Callback<Profile> {
                override fun onResponse(call: Call<Profile>, response: Response<Profile>) {
                    if (response.isSuccessful) {
                        Log.d("ProfileViewModel", "Successful Response Body: ${response.body()}")

                    } else {
                        val errorBody = response.errorBody()?.string()
                        Log.e("ProfileViewModel", "Error Response Body: $errorBody")
                    }
                }

                override fun onFailure(call: Call<Profile>, t: Throwable) {
                    Log.e("ProfileViewModel", "Network Error: ${t.message}")
                }
            })
        }
    }

    //  Remove getUserName.  It's redundant if getUserProfile fetches all data.

    fun uploadAndSaveProfileImage(userId: String, byteArray: ByteArray) {
        viewModelScope.launch {
            val storageRef = Firebase.storage.reference
            val profileImageRef =
                storageRef.child("profile_images/${userId}-${UUID.randomUUID()}.jpg")

            try {
                val uploadTask = profileImageRef.putBytes(byteArray).await()
                val imageUrl = uploadTask.metadata?.reference?.downloadUrl?.await()

                if (imageUrl != null) {
                    //  Llamar a la API para guardar la URL
                    RetrofitClient.apiService.updateUserProfile(
                        userId,
                        _profile.value?.nombre ?: "",
                        _profile.value?.email ?: "",
                        imageUrl.toString()
                    ).enqueue(object : Callback<String> {
                        override fun onResponse(call: Call<String>, response: Response<String>) {
                            if (response.isSuccessful) {
                                Log.d("ProfileViewModel", "URL de la imagen guardada: $imageUrl")
                                // After successful upload, update the profile LiveData
                                getUserProfile(_profile.value?.email ?: "") // Refresh profile
                            } else {
                                Log.e(
                                    "ProfileViewModel",
                                    "Error al guardar la URL: ${response.errorBody()?.string()}"
                                )
                            }
                        }

                        override fun onFailure(call: Call<String>, t: Throwable) {
                            Log.e("ProfileViewModel", "Error de red: ${t.message}")
                        }
                    })
                }
            } catch (e: Exception) {
                Log.e("ProfileViewModel", "Error al subir la imagen: ${e.message}")
            }
        }
    }

    fun updateUserProfile(userId: String, nombre: String, email: String) {
        viewModelScope.launch {
            RetrofitClient.apiService.updateUserProfile(
                userId,
                nombre,
                email,
                _profile.value?.fotoPerfil ?: ""
            ).enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    if (response.isSuccessful) {
                        Log.d("ProfileViewModel", "Perfil actualizado")
                        // After successful update, refresh the profile
                        getUserProfile(_profile.value?.email ?: "")
                    } else {
                        Log.e(
                            "ProfileViewModel",
                            "Error al actualizar el perfil: ${response.errorBody()?.string()}"
                        )
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    Log.e("ProfileViewModel", "Error de red: ${t.message}")
                }
            })
        }
    }
=======
class ProfileViewModel {
>>>>>>> 56d9615d32097a07c51d0e697c2172a2e8e7781e
}