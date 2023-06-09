package com.example.lahoradelidiota.database

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import com.example.lahoradelidiota.databinding.ActivityLoginBinding
import com.example.lahoradelidiota.photoactivity.ImageActivity
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var sharedPreferences: SharedPreferences

    private val SHARED_PREFS_NAME = "MiSharedPreferences"
    private val KEY_TEXTO_GUARDADO = "correoGuardado"
    private val KEY_PASSWOR_SAVE = "passwordGuardado"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        val binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        sharedPreferences = getSharedPreferences("MiSharedPreferences", Context.MODE_PRIVATE)

        val textoGuardado = sharedPreferences.getString("textoGuardado", "")
        binding.correoedit.setText(textoGuardado)

        val otroTextoGuardado = sharedPreferences.getString(KEY_PASSWOR_SAVE, "")
        binding.passwordedit.setText(otroTextoGuardado)

        binding.signInButton.setOnClickListener {
            val texto = binding.correoedit.text.toString()
            if (texto.isNotEmpty()) {
                guardarCorreo(texto)
            }
        }

        binding.signUpButton.setOnClickListener {
            val email = binding.correoedit.text.toString()
            val pass = binding.passwordedit.text.toString()


            if (email.isNotEmpty() && pass.isNotEmpty()) {
                firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener {
                    if (it.isSuccessful) {
                        val intent = Intent(this, ImageActivity::class.java)
                    } else {
                        Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(
                    this,
                    "Revisa que los datos sean correctos SEEE!!",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }




        binding.signInButton.setOnClickListener {
            val email = binding.correoedit.text.toString()

            val pass = binding.passwordedit.text.toString()



            if (email.isNotEmpty() && pass.isNotEmpty()) {

                firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
                    if (it.isSuccessful) {
                        val texto = binding.correoedit.text.toString()
                        guardarCorreo(texto)
                        val otroTexto = binding.passwordedit.text.toString()
                        guardarContraseña(otroTexto)
                        val intent = Intent(this, DbIdiotRecycler::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, "Datos incorrectos SEEEE!!", Toast.LENGTH_SHORT).show()

                    }
                }
            } else {
                Toast.makeText(this, "Revisa tus datos Seeee !!", Toast.LENGTH_SHORT).show()

            }
        }

    }
    fun guardarCorreo(texto: String) {
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString("correoGuardado", texto)
        editor.apply()
    }

    fun guardarContraseña(otroTexto: String) {
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString(KEY_PASSWOR_SAVE, otroTexto)
        editor.apply()
    }


}