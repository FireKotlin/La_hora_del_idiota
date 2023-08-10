package com.example.lahoradelidiota.database

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.lahoradelidiota.R
import com.example.lahoradelidiota.databinding.ActivityAddIdiotBinding
import com.example.lahoradelidiota.databinding.ActivityDbIdiotRecyclerBinding
import com.example.lahoradelidiota.main.MainActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AddIdiot : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityAddIdiotBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val addtoolbar = binding.addToolbar
        addtoolbar.title = "Añadir idiota"
        addtoolbar.setNavigationIcon(R.drawable.baseline_arrow_back_24)
        addtoolbar.setNavigationOnClickListener {
            startActivity(Intent(this, DbIdiotRecycler::class.java))
        }


        binding.acceptButton.setOnClickListener {
            val id = binding.numEdit.text.toString()

            val data = hashMapOf(
                "ImageUrl" to binding.urlEdit.text.toString(),
                "Numero" to binding.numEdit.text.toString(),
                "Nombre" to binding.nombreEdit.text.toString(),
                "Nivel de Idiotez" to binding.nivelEdit.text.toString(),
                "Sitio Frecuente" to binding.sitedit.text.toString(),
                "Habilidad especial" to binding.habilidadEdit.text.toString(),
                "Descripcion" to binding.descripcionEdit.text.toString()
            )

            db.collection("idiotas").document(id).set(data)
                .addOnSuccessListener {
                }
                .addOnFailureListener { e ->
                    Log.e("AddIdiot", "Error al agregar el idiota", e)
                }
        }
    }
}