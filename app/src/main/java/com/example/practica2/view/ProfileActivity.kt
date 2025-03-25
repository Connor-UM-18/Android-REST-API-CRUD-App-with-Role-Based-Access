package com.example.practica2.view

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.practica2.R
import com.example.practica2.controller.ProfileViewModel
import com.example.practica2.utils.SessionManager
import com.squareup.picasso.Picasso
import java.io.ByteArrayOutputStream

class ProfileActivity : AppCompatActivity() {

    private lateinit var sessionManager: SessionManager
    private val profileViewModel: ProfileViewModel by viewModels()
    private lateinit var profileImage: ImageView
    private lateinit var nameTextView: TextView
    private lateinit var emailTextView: TextView
    private lateinit var passwordEditText: EditText
    private lateinit var togglePasswordButton: ImageView
    private lateinit var editProfileButton: Button

    // Lanzador para la selección de imágenes
    private val imagePickerLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                val selectedImageUri = data?.data
                if (selectedImageUri != null) {
                    profileImage.setImageURI(selectedImageUri)
                    // Llamar a la función para subir la imagen
                    uploadProfileImage()
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        sessionManager = SessionManager(this)

        profileImage = findViewById(R.id.profile_image)
        nameTextView = findViewById(R.id.profile_name)
        emailTextView = findViewById(R.id.profile_email)
        passwordEditText = findViewById(R.id.profile_password)
        togglePasswordButton = findViewById(R.id.toggle_password)
        editProfileButton = findViewById(R.id.edit_profile_button)

        // Cargar los datos del usuario desde la sesión (si están disponibles)
        val userEmail = sessionManager.getUserEmail()
        emailTextView.text = userEmail

        profileViewModel.getUserProfile(userEmail ?: "")

        profileViewModel.profile.observe(this) { user ->
            if (user != null) {
                nameTextView.text = user.nombre
            }
            passwordEditText.setText("********") // Ocultar la contraseña
            if (user != null) {
                if (user.fotoPerfil.isNotEmpty()) {
                    Picasso.get().load(user.fotoPerfil).into(profileImage)
                }
            }
        }

        // Alternar visibilidad de la contraseña
        togglePasswordButton.setOnClickListener {
            if (passwordEditText.inputType == 129) { // 129 = password oculta
                passwordEditText.inputType = 145 // 145 = texto visible
                togglePasswordButton.setImageResource(R.drawable.ic_visibility)
            } else {
                passwordEditText.inputType = 129
                togglePasswordButton.setImageResource(R.drawable.ic_visibility_off)
            }
        }

        // Botón para editar perfil
        editProfileButton.setOnClickListener {
            val intent = Intent(this, EditProfileActivity::class.java)
            startActivity(intent)
        }

        // Botón para seleccionar imagen
        profileImage.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            imagePickerLauncher.launch(intent)
        }
    }

    private fun uploadProfileImage() {
        val bitmap = (profileImage.drawable as BitmapDrawable).bitmap
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos) // Comprimir la imagen (ajusta la calidad)
        val byteArray = baos.toByteArray()

        val userId = sessionManager.getUserEmail() ?: "" // Obtener el ID del usuario
        profileViewModel.uploadAndSaveProfileImage(userId, byteArray)
    }
}