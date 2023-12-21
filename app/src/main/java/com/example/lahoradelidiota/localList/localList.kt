package com.example.lahoradelidiota.localList

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lahoradelidiota.R
import com.example.lahoradelidiota.databinding.ActivityLocalListBinding
import com.example.lahoradelidiota.main.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class LocalList : AppCompatActivity() {

    private lateinit var binding: ActivityLocalListBinding
    private lateinit var adapter: LocalAdapter
    private lateinit var userId: String // Variable para almacenar el ID del usuario actual

    private val addLocalLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val nuevoIdiota = result.data?.getParcelableExtra<IdiotaLocal>("nuevoIdiota")
                nuevoIdiota?.let {
                    // Agregar el nuevo idiota a Firebase
                    agregarNuevoIdiotaFirebase(it)
                }
            }
        }

    private val localItems = mutableListOf<IdiotaLocal>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLocalListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar = binding.localListTb
        toolbar.title = "Mis Idiotas"
        toolbar.setNavigationIcon(R.drawable.baseline_arrow_back_24)
        toolbar.setNavigationOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        // Recuperar el ID del usuario actual
        userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""

        // Configurar RecyclerView y adaptador
        adapter = LocalAdapter()
        binding.recyclerIdiot.layoutManager = LinearLayoutManager(this)
        binding.recyclerIdiot.adapter = adapter

        // Configurar el botón de agregar utilizando el launcher
        binding.addBttn.setOnClickListener {
            val intent = Intent(this, AddLocal::class.java)
            addLocalLauncher.launch(intent)
        }

        adapter.setOnItemClickListener { idiotaLocal ->
            // Abrir la actividad de detalles con el idiota seleccionado
            val intent = Intent(this, DetailLocal::class.java)
            intent.putExtra("idiotaLocal", idiotaLocal)
            startActivity(intent)
        }

        // Cargar la lista desde Firebase
        cargarDatosDesdeFirebase()

        // Cargar la lista en el adaptador
        adapter.submitList(localItems)
    }

    private fun cargarDatosDesdeFirebase() {
        val referenciaUsuario =
            FirebaseDatabase.getInstance().getReference("usuarios/$userId/idiotasLocales")

        referenciaUsuario.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                localItems.clear()
                for (childSnapshot in snapshot.children) {
                    val idiotaLocal = childSnapshot.getValue(IdiotaLocal::class.java)
                    idiotaLocal?.let {
                        localItems.add(it)
                    }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                // Manejar el error de la base de datos
            }
        })
    }

    private fun agregarNuevoIdiotaFirebase(nuevoIdiota: IdiotaLocal) {
        val referenciaUsuario =
            FirebaseDatabase.getInstance().getReference("usuarios/$userId/idiotasLocales")

        referenciaUsuario.push().setValue(nuevoIdiota)
    }

    override fun onDestroy() {
        super.onDestroy()

    }
}
