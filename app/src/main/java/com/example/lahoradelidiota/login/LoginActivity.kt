package com.example.lahoradelidiota.login


import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.lahoradelidiota.database.PantallaIdiota
import com.example.lahoradelidiota.databinding.ActivityLoginBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        val binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        sharedPreferences = getSharedPreferences("MiSharedPreferences", Context.MODE_PRIVATE)

        // Verificar si el usuario ya está autenticado
        if (firebaseAuth.currentUser != null) {
            abrirActividadPrincipal()
            return
        }

        binding.signUpButton.setOnClickListener {
            val email = binding.correoedit.text.toString()
            val pass = binding.passwordedit.text.toString()

            if (email.isNotEmpty() && pass.isNotEmpty()) {
                registrarUsuario(email, pass)
            } else {
                mostrarMensaje("Revisa que los datos sean correctos SEEE!!")
            }
        }

        binding.signInButton.setOnClickListener {
            val email = binding.correoedit.text.toString()
            val pass = binding.passwordedit.text.toString()

            if (email.isNotEmpty() && pass.isNotEmpty()) {
                iniciarSesion(email, pass)
            } else {
                mostrarMensaje("Revisa tus datos Seeee !!")
            }
        }
    }

    private fun registrarUsuario(email: String, pass: String) {
        firebaseAuth.createUserWithEmailAndPassword(email, pass)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Registro exitoso, abrir la actividad especial
                    abrirActividadEspecial()
                } else {
                    // Error en el registro, mostrar mensaje
                    mostrarMensaje("Error al crear usuario: ${task.exception?.message}")
                }
            }
    }

    private fun abrirActividadEspecial() {
        val intent = Intent(this, PantallaIdiota::class.java)
        startActivity(intent)
        finish()
    }

    private fun iniciarSesion(email: String, pass: String) {
        firebaseAuth.signInWithEmailAndPassword(email, pass)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    abrirActividadPrincipal()
                } else {
                    mostrarMensaje("Datos incorrectos SEEEE!!")
                }
            }
    }

    private fun abrirActividadPrincipal() {
        val intent = Intent(this, CloseActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun mostrarMensaje(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }
}
