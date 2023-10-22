package com.example.lahoradelidiota.database

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import com.example.lahoradelidiota.databinding.ActivityLoginAdminBinding
import com.google.firebase.auth.FirebaseAuth

class LoginAdmin : AppCompatActivity() {

    private lateinit var binding :  ActivityLoginAdminBinding

    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityLoginAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.signUpButtonA.setOnClickListener{
            validarDatos()
        }
    }

    var email = ""
    var password = ""
    private fun validarDatos() {
        email = binding.correoeditA.text.toString().trim()
        password = binding.passwordeditA.text.toString().trim()

        if (email.isEmpty()){
            binding.correoeditA.error = "Ingresa el correo animal!!"
            binding.correoeditA.requestFocus()
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.correoeditA.error = "Correo no valido Idiota"
            binding.correoeditA.requestFocus()

        }
    }
}