package com.example.lahoradelidiota.database

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lahoradelidiota.R
import com.example.lahoradelidiota.databinding.ActivityDbIdiotRecyclerBinding
import com.example.lahoradelidiota.main.MainActivity
import com.example.lahoradelidiota.others.Idiota
import com.google.firebase.firestore.FirebaseFirestore

class DbIdiotRecycler : AppCompatActivity() {

    private lateinit var binding: ActivityDbIdiotRecyclerBinding
    private val db = FirebaseFirestore.getInstance()


    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDbIdiotRecyclerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerIdiotDb.layoutManager = LinearLayoutManager(this)

        val adapter = DbAdapter(this)
        binding.recyclerIdiotDb.adapter = adapter
        adapter.notifyDataSetChanged()
        adapter.setOnItemClickListener { idiota ->
            deleteIdiota(idiota)
        }

        // Obtener la lista de idiotas de Firestore y configurar el adaptador
        db.collection("idiotas")
            .get()
            .addOnSuccessListener { result ->
                val idiotaList = mutableListOf<Idiota>() // Cambio de Task a Idiota
                for (document in result) {
                    val imagenUrl = document.getString("imagenUrl") ?: ""
                    val numeroDeIdiota = document.getString("numeroDeIdiota") ?: ""
                    val nombre = document.getString("nombre") ?: ""
                    val nivel = document.getString("nivel") ?: ""
                    val site = document.getString("site") ?: ""
                    val habilidadEspecial = document.getString("habilidadEspecial") ?: ""
                    val descripcion = document.getString("descripcion") ?: ""

                    val idiota = Idiota(imagenUrl, numeroDeIdiota, nombre, nivel, site, habilidadEspecial, descripcion)
                    idiotaList.add(idiota)
                }
                val sortedIdiotaList = idiotaList.sortedBy { it.numeroDeIdiota.toIntOrNull() }
                adapter.submitList(sortedIdiotaList)

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
    private fun deleteIdiota(idiota: Idiota) {
        val collectionRef = db.collection("idiotas")
        val documentRef = collectionRef.document(idiota.numeroDeIdiota)

        documentRef.delete()
            .addOnSuccessListener {
                Toast.makeText(this, "Elemento eliminado exitosamente", Toast.LENGTH_SHORT).show()
                // Obtén nuevamente la lista actualizada y actualiza el adaptador
                refreshAdapter()
            }
            .addOnFailureListener { e ->
                Log.e("DbIdiotRecycler", "Error al eliminar el elemento", e)
            }
    }
    private fun refreshAdapter() {
        db.collection("idiotas")
            .get()
            .addOnSuccessListener { result ->
                val idiotaList = mutableListOf<Idiota>()
                for (document in result) {
                    TODO()
                }
                val sortedIdiotaList = idiotaList.sortedBy { it.numeroDeIdiota.toIntOrNull() }
                val adapter = binding.recyclerIdiotDb.adapter as DbAdapter
                adapter.submitList(sortedIdiotaList)
            }
            .addOnFailureListener { e ->
                Log.e("DbIdiotRecycler", "Error al obtener la lista de idiotas", e)
            }
    }
}