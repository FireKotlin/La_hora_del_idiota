package com.example.lahoradelidiota.localList

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lahoradelidiota.R
import com.example.lahoradelidiota.databinding.ActivityLocalListBinding
import com.example.lahoradelidiota.localList.db.AppDatabase
import com.example.lahoradelidiota.localList.db.IdiotaRepository
import com.example.lahoradelidiota.localList.db.IdiotaViewModel
import com.example.lahoradelidiota.localList.db.IdiotaViewModelFactory
import com.example.lahoradelidiota.main.MainActivity

class LocalList : AppCompatActivity() {

    private lateinit var binding: ActivityLocalListBinding
    private lateinit var adapter: LocalAdapter
    private val idiotaRepository: IdiotaRepository by lazy {
        // Obtener la base de datos
        val database = AppDatabase.getDatabase(this)
        // Obtener el DAO
        val dao = database.UserDao()
        // Crear el repositorio
        IdiotaRepository(dao)
    }
    private val idiotaViewModel: IdiotaViewModel by viewModels {
        IdiotaViewModelFactory(idiotaRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLocalListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        setupRecyclerView()
        observeData()

        binding.addBttn.setOnClickListener {
            val intent = Intent(this, AddLocal::class.java)
            startActivity(intent)
        }
    }

    private fun setupToolbar() {
        val toolbar = binding.localListTb
        toolbar.title = "Mis Idiotas"
        toolbar.setNavigationIcon(R.drawable.baseline_arrow_back_24)
        toolbar.setNavigationOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    private fun setupRecyclerView() {
        adapter = LocalAdapter().apply {
            setOnItemClickListener { idiotaLocal ->
                val intent = Intent(this@LocalList, DetailLocal::class.java).apply {
                    putExtra("IDIOTA_ID", idiotaLocal.id)
                }
                startActivity(intent)
            }
        }
        binding.recyclerIdiot.layoutManager = LinearLayoutManager(this)
        binding.recyclerIdiot.adapter = adapter
    }

    private fun observeData() {
        idiotaViewModel.allIdiotas.observe(this) { listaIdiotas ->
            adapter.submitList(listaIdiotas)
        }
    }
}
