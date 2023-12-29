package com.example.lahoradelidiota.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.lahoradelidiota.databinding.ActivityCloseBinding
import com.google.firebase.auth.FirebaseAuth
import com.example.lahoradelidiota.main.MainActivity

class CloseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityCloseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configurar el botón para cerrar sesión
        binding.cerrarBttn.setOnClickListener {
            cerrarSesion()
        }

        // Configurar el botón para cancelar
        binding.cancelarBttn.setOnClickListener {
            // Llevar al usuario a la actividad MainActivity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // Opcional: cierra esta actividad si ya no la necesitas en la pila
        }
    }

    private fun cerrarSesion() {
        // Aquí realizas las acciones necesarias para cerrar la sesión
        // Por ejemplo, puedes utilizar FirebaseAuth para cerrar la sesión
        FirebaseAuth.getInstance().signOut()

        // Redirigir al usuario a la actividad de inicio de sesión
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finishAffinity() // Cierra todas las actividades abiertas en la pila
    }
}
