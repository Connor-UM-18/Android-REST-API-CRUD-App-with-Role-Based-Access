package com.example.practica2.view

<<<<<<< HEAD
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.practica2.R
import com.example.practica2.controller.ProfileViewModel
import com.example.practica2.utils.SessionManager
import com.squareup.picasso.Picasso

class MainActivity : AppCompatActivity() {

    private lateinit var sessionManager: SessionManager
    private val profileViewModel: ProfileViewModel by viewModels()

    // UI elements
    private lateinit var profileImage: ImageView
    private lateinit var nameTextView: TextView
    private lateinit var emailTextView: TextView
    private lateinit var passwordEditText: EditText
    private lateinit var togglePasswordButton: ImageView
    private var adminButton: Button? = null
    private lateinit var logoutButton: Button
    private lateinit var editProfileButton: Button
=======
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.practica2.R
import com.example.practica2.utils.SessionManager
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var sessionManager: SessionManager
>>>>>>> 56d9615d32097a07c51d0e697c2172a2e8e7781e

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

<<<<<<< HEAD
        // Initialize SessionManager and UI elements
        sessionManager = SessionManager(this)

        profileImage = findViewById(R.id.profile_image)
        nameTextView = findViewById(R.id.profile_name)
        emailTextView = findViewById(R.id.profile_email)
        passwordEditText = findViewById(R.id.profile_password)
        togglePasswordButton = findViewById(R.id.toggle_password)
        adminButton = findViewById(R.id.admin_button)
        logoutButton = findViewById(R.id.logout_button)
        editProfileButton = findViewById(R.id.edit_profile_button)


        val userEmail = sessionManager.getUserEmail()
        val userName = sessionManager.getUserNombre()
        emailTextView.text = userEmail
        nameTextView.text = userName
        // Observe the profile data from the ViewModel
        profileViewModel.profile.observe(this) { profile ->
            if (profile != null) {
                nameTextView.text = profile.nombre
                // Load profile image using Picasso (or your preferred library)
                Picasso.get().load(profile.fotoPerfil).into(profileImage)
            } else {
                // Handle the case where profile data is not available (e.g., error)
                nameTextView.text = "Nombre no disponible"
                profileImage.setImageResource(R.drawable.user)
            }
        }

        // Fetch user profile data
        if (userEmail != null) {
            profileViewModel.getUserProfile(userEmail)
        }

        // Password visibility toggle
        togglePasswordButton.setOnClickListener {
            if (passwordEditText.inputType == 129) {
                passwordEditText.inputType = 145
                togglePasswordButton.setImageResource(R.drawable.ic_visibility)
            } else {
                passwordEditText.inputType = 129
                togglePasswordButton.setImageResource(R.drawable.ic_visibility_off)
            }
        }

        // Edit profile button click
        editProfileButton.setOnClickListener {
            val intent = Intent(this, EditProfileActivity::class.java)
            startActivity(intent)
        }

        // Admin button visibility and click
        if (sessionManager.getUserRol() == "admin") {
            adminButton?.visibility = View.VISIBLE
            adminButton?.setOnClickListener {
                val intent = Intent(this, AdminActivity::class.java)
                startActivity(intent)
            }
        } else {
            adminButton?.visibility = View.GONE
        }

        // Logout button click
        logoutButton.setOnClickListener {
            sessionManager.logoutUser()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
=======
        // Inicializar la sesión del usuario
        sessionManager = SessionManager(this)

        // Inicializar la Toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Configurar el DrawerLayout y NavigationView
        drawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.navigation_view)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        val navController = navHostFragment.navController

        NavigationUI.setupWithNavController(navView, navController)
        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)

        // Actualizar el email en el Navigation Drawer
        updateNavHeader()
    }

    private fun updateNavHeader() {
        val navigationView: NavigationView = findViewById(R.id.navigation_view)
        val headerView = navigationView.getHeaderView(0) // Obtener la vista del header
        val userEmailTextView: TextView = headerView.findViewById(R.id.user_email)

        // Obtener el email almacenado en la sesión y mostrarlo
        val userEmail = sessionManager.getUserEmail()
        userEmailTextView.text = userEmail ?: "Correo no disponible"
    }

    override fun onSupportNavigateUp(): Boolean {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        val navController = navHostFragment.navController
        return NavigationUI.navigateUp(navController, drawerLayout) || super.onSupportNavigateUp()
>>>>>>> 56d9615d32097a07c51d0e697c2172a2e8e7781e
    }
}