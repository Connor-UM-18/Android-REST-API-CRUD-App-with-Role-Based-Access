package com.example.practica2.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.practica2.R
import com.example.practica2.controller.RegisterViewModel // Importa el ViewModel
import com.example.practica2.databinding.ActivityRegisterBinding // Importa el Binding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val registerViewModel: RegisterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Botón de Registrar
        binding.registerButton.setOnClickListener {
            val email = binding.emailEditText.text.toString().trim()
            val password = binding.passwordEditText.text.toString().trim()
            val nombre = binding.nombreEditText.text.toString().trim() // Obtén el nombre

            if (email.isNotEmpty() && password.isNotEmpty() && nombre.isNotEmpty()) {
                binding.registerProgressBar.visibility = View.VISIBLE // Mostrar ProgressBar
                registerViewModel.register(email, password, nombre) // Llama a la función register
            } else {
                Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
            }
        }

        // Observar cambios en el ViewModel
        registerViewModel.registerResponse.observe(this) { message ->
            binding.registerProgressBar.visibility = View.GONE // Ocultar ProgressBar
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

            if (message.startsWith("Registro exitoso")) {
                // Navegar a la siguiente actividad (por ejemplo, Login o Main)
                startActivity(Intent(this, MainActivity::class.java)) // Cambia MainActivity por la actividad a la que quieras ir
                finish()
            }
        }

        // Ir a pantalla de login (opcional, si quieres un enlace)
        binding.loginTextView.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}