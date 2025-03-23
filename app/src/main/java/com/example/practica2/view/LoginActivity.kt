package com.example.practica2.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.practica2.controller.LoginViewModel
import com.example.practica2.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Botón de Login
        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText.text.toString().trim()
            val password = binding.passwordEditText.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                binding.loginProgressBar.visibility = View.VISIBLE  // Mostrar ProgressBar
                loginViewModel.login(email, password)
            } else {
                Toast.makeText(this, "Por favor, completa los campos", Toast.LENGTH_SHORT).show()
            }
        }

        // Observar cambios en el ViewModel
        loginViewModel.loginResponse.observe(this) { message ->
            binding.loginProgressBar.visibility = View.GONE  // Ocultar ProgressBar
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

            if (message.startsWith("Login exitoso")) {
                // Navegar a MainActivity después del login exitoso
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }

        // Ir a pantalla de registro
        binding.registerTextView.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}
