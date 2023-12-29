package com.example.lahoradelidiota.database

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.lahoradelidiota.R
import com.example.lahoradelidiota.databinding.ActivityPassBinding

class PassActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPassBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_pass)

        binding.signInButton.setOnClickListener {
            val password = binding.passwordedit.text.toString()
            if (isValidPassword(password)) {
                // Contraseña correcta, navegar a la actividad deseada
                val intent = Intent(this, DbIdiotRecycler::class.java)
                startActivity(intent)
            } else {
                // Contraseña incorrecta, mostrar mensaje de error
                Toast.makeText(this, "Contraseña incorrecta", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun isValidPassword(password: String): Boolean {
        // Implementa tu lógica de validación de contraseña aquí
        return password == "INGING"
    }
}
