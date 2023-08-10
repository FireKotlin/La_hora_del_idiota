package com.example.lahoradelidiota.database

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lahoradelidiota.R
import com.example.lahoradelidiota.databinding.ActivityDbIdiotRecyclerBinding
import com.example.lahoradelidiota.main.MainActivity
import com.example.lahoradelidiota.others.Idiota
import com.google.firebase.firestore.FirebaseFirestore

class DbIdiotRecycler : AppCompatActivity() {

    private lateinit var binding: ActivityDbIdiotRecyclerBinding
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDbIdiotRecyclerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerIdiotDb.layoutManager = LinearLayoutManager(this)

        val adapter = DbAdapter()
        binding.recyclerIdiotDb.adapter = adapter

        // Obtener la lista de idiotas de Firestore y configurar el adaptador
        db.collection("idiotas")
            .get()
            .addOnSuccessListener { result ->
                val idiotaList = mutableListOf<Idiota>() // Cambio de Task a Idiota
                for (document in result) {
                    val imageUrl = document.getString("imageUrl") ?: ""
                    val numeroDeIdiota = document.getString("numeroDeIdiota") ?: ""
                    val nombre = document.getString("nombre") ?: ""
                    val nivel = document.getString("nivel") ?: ""
                    val site = document.getString("site") ?: ""
                    val habilidadEspecial = document.getString("habilidadEspecial") ?: ""
                    val descripcion = document.getString("descripcion") ?: ""

                    val idiota = Idiota(imageUrl, numeroDeIdiota, nombre, nivel, site, habilidadEspecial, descripcion)
                    idiotaList.add(idiota)
                }
                adapter.submitList(idiotaList)
            }
            .addOnFailureListener { e ->
                Log.e("DbIdiotRecycler", "Error al obtener la lista de idiotas", e)
            }
        // Toolbar
        val dbToolbar = binding.dbToolbar
        dbToolbar.title = "Base de datos idiota"
        dbToolbar.setNavigationIcon(R.drawable.baseline_arrow_back_24)
        dbToolbar.setNavigationOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        binding.fab.setOnClickListener {
            startActivity(Intent(this, AddIdiot::class.java))
        }
    }
}