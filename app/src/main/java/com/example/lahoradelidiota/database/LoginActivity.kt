package com.example.lahoradelidiota.database

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.lahoradelidiota.databinding.ActivityLoginBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var sharedPreferences: SharedPreferences

    private val KEY_PASSWORD_SAVE = "passwordGuardado"
    private val KEY_ROLE = "role"
    private val KEY_USER_LOGGED_IN = "userLoggedIn"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        val binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        sharedPreferences = getSharedPreferences("MiSharedPreferences", Context.MODE_PRIVATE)

        val textoGuardado = sharedPreferences.getString("textoGuardado", "")
        binding.correoedit.setText(textoGuardado)

        val otroTextoGuardado = sharedPreferences.getString(KEY_PASSWORD_SAVE, "")
        binding.passwordedit.setText(otroTextoGuardado)

        val user = firebaseAuth.currentUser
        val userId = user?.uid

        val defaultRole = "user"
        val usersRef = FirebaseDatabase.getInstance().getReference("users")
        usersRef.child(userId ?: "").child(KEY_ROLE).setValue(defaultRole)

        binding.signInButton.setOnClickListener {
            val email = binding.correoedit.text.toString()
            val pass = binding.passwordedit.text.toString()

            if (email.isNotEmpty() && pass.isNotEmpty()) {
                firebaseAuth.signInWithEmailAndPassword(email, pass)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val user = firebaseAuth.currentUser
                            checkAdminRole(user?.uid)
                            saveUserLoggedIn(true)
                        } else {
                            Toast.makeText(this, "Datos incorrectos SEEEE!!", Toast.LENGTH_SHORT).show()
                        }
                    }
            } else {
                Toast.makeText(this, "Revisa tus datos Seeee !!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun checkAdminRole(userId: String?) {
        val usersRef = FirebaseDatabase.getInstance().getReference("users")
        usersRef.child(userId ?: "").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val role = snapshot.child(KEY_ROLE).getValue(String::class.java)
                    if (role == "admin") {
                        val intent = Intent(this@LoginActivity, DbIdiotRecycler::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this@LoginActivity, "No tienes permisos de administrador", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Maneja errores de lectura de la base de datos si es necesario
            }
        })
    }

    private fun saveUserLoggedIn(isLoggedIn: Boolean) {
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putBoolean(KEY_USER_LOGGED_IN, isLoggedIn)
        editor.apply()
    }
}
