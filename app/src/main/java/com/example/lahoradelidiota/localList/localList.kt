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

class LocalList : AppCompatActivity() {

    private lateinit var binding: ActivityLocalListBinding
    private lateinit var adapter: LocalAdapter
    private lateinit var localStorage: LocalStorage

    // Use ActivityResultContracts para manejar el resultado de AddLocal
    private val addLocalLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val nuevoIdiota = result.data?.getParcelableExtra<IdiotaLocal>("nuevoIdiota")
                nuevoIdiota?.let {
                    localItems.add(it)
                    adapter.submitList(localItems.toList())
                    localStorage.saveLocalItems(localItems.toList())
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

        // Inicializar la instancia de LocalStorage
        localStorage = LocalStorage.getInstance(this)

        // Recuperar la lista almacenada en SharedPreferences
        localItems.addAll(localStorage.getLocalItems())

        // Configurar RecyclerView y adaptador
        adapter = LocalAdapter()
        binding.recyclerIdiot.layoutManager = LinearLayoutManager(this)
        binding.recyclerIdiot.adapter = adapter

        // Configurar el botón de agregar utilizando el launcher
        binding.addBttn.setOnClickListener {
            val intent = Intent(this, AddLocal::class.java)
            addLocalLauncher.launch(intent)
        }

        // Configurar el evento de clic en el adaptador
        adapter.setOnItemClickListener { idiotaLocal ->
            // Lógica para manejar el clic en un elemento de la lista
            // Puedes abrir la pantalla de detalles o hacer lo que sea necesario
        }

        // Cargar la lista en el adaptador
        adapter.submitList(localItems)
    }

    override fun onDestroy() {
        super.onDestroy()

        // Guardar la lista actual en SharedPreferences
        localStorage.saveLocalItems(localItems)
    }
}
