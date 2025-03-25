//  EditProfileActivity.kt
package com.example.practica2.view

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.practica2.R
import com.example.practica2.controller.ProfileViewModel
import com.example.practica2.utils.SessionManager
import com.squareup.picasso.Picasso
import java.io.ByteArrayOutputStream

class EditProfileActivity : AppCompatActivity() {

    private lateinit var sessionManager: SessionManager
    private val profileViewModel: ProfileViewModel by viewModels()
    private lateinit var profileImage: ImageView
    private lateinit var nameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var saveButton: Button

    //  Lanzador para la selección de imágenes
    private val imagePickerLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                val selectedImageUri = data?.data
                if (selectedImageUri != null) {
                    profileImage.setImageURI(selectedImageUri)
                    //  Llamar a la función para subir la imagen
                    uploadProfileImage()
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        sessionManager = SessionManager(this)

        profileImage = findViewById(R.id.profile_image)
        nameEditText = findViewById(R.id.edit_name)
        emailEditText = findViewById(R.id.edit_email)
        saveButton = findViewById(R.id.save_button)

        //  Cargar los datos del usuario desde la sesión (si están disponibles)
        val userEmail = sessionManager.getUserEmail()
        emailEditText.setText(userEmail)

        profileViewModel.getUserProfile(userEmail ?: "")

        profileViewModel.profile.observe(this) { user ->
            if (user != null) {
                nameEditText.setText(user.nombre)
                if (user.fotoPerfil.isNotEmpty()) {
                    Picasso.get().load(user.fotoPerfil).into(profileImage)
                }
            }
        }

        //  Botón para seleccionar imagen
        profileImage.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            imagePickerLauncher.launch(intent)
        }

        //  Botón para guardar cambios
        saveButton.setOnClickListener {
            val updatedName = nameEditText.text.toString()
            val updatedEmail = emailEditText.text.toString()

            //  Aquí se pueden agregar validaciones si es necesario

            val userId = sessionManager.getUserEmail() ?: ""

            if ((profileImage.drawable as BitmapDrawable).bitmap != null) {
                uploadProfileImage()
            } else {
                profileViewModel.updateUserProfile(userId, updatedName, updatedEmail)
            }
        }
    }

    private fun uploadProfileImage() {
        val bitmap = (profileImage.drawable as BitmapDrawable).bitmap
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val byteArray = baos.toByteArray()
        val userId = sessionManager.getUserEmail() ?: ""
        profileViewModel.uploadAndSaveProfileImage(userId, byteArray)
    }
}