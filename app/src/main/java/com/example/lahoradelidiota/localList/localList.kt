package com.example.lahoradelidiota.localList

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lahoradelidiota.R
import com.example.lahoradelidiota.databinding.ActivityLocalListBinding
import com.example.lahoradelidiota.main.MainActivity
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken

class LocalList : AppCompatActivity() {

    private lateinit var binding: ActivityLocalListBinding
    private lateinit var adapter: LocalAdapter

    private val addLocalLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val nuevoIdiota =
                    result.data?.getParcelableExtra<IdiotaLocal>("nuevoIdiota")
                nuevoIdiota?.let {
                    agregarNuevoIdiotaLocal(it)
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_local_list)

        val gson = GsonBuilder()
            .registerTypeAdapter(Uri::class.java, UriTypeAdapter())
            .create()

        // Cargar la lista local
        cargarListaLocal(gson)

        val toolbar = binding.localListTb
        toolbar.title = "Mis Idiotas"
        toolbar.setNavigationIcon(R.drawable.baseline_arrow_back_24)
        toolbar.setNavigationOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        adapter = LocalAdapter()
        binding.recyclerIdiot.layoutManager = LinearLayoutManager(this)
        binding.recyclerIdiot.adapter = adapter

        binding.addBttn.setOnClickListener {
            val intent = Intent(this, AddLocal::class.java)
            addLocalLauncher.launch(intent)
        }

        adapter.setOnItemClickListener { idiotaLocal ->
            val intent = Intent(this, DetailLocal::class.java)
            intent.putExtra("idiotaLocal", idiotaLocal)
            startActivityForResult(intent, DetailLocal.DELETE_RESULT_CODE)
        }

        // Actualizar la lista en el adaptador después de cargarla
        adapter.submitList(localItems.toList())
    }

    fun agregarNuevoIdiotaLocal(nuevoIdiota: IdiotaLocal) {
        localItems.add(nuevoIdiota)
        // Guardar la lista local después de agregar un nuevo elemento
        guardarListaLocal()
        // Notificar al adaptador sobre el cambio
        adapter.submitList(localItems.toList())
    }

    internal fun guardarListaLocal() {
        val sharedPreferences = getPreferences(MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val gson = GsonBuilder()
            .registerTypeAdapter(Uri::class.java, UriTypeAdapter())
            .create()
        val json = gson.toJson(localItems)
        editor.putString("localItems", json)
        editor.apply()
    }

    private fun cargarListaLocal(gson: Gson) {
        val sharedPreferences = getPreferences(MODE_PRIVATE)
        val json = sharedPreferences.getString("localItems", "")
        val type = object : TypeToken<List<IdiotaLocal>>() {}.type
        localItems.clear() // Limpiar la lista antes de cargarla
        localItems.addAll(gson.fromJson(json, type) ?: emptyList())
    }

    fun eliminarIdiotaLocal(idiotaLocal: IdiotaLocal) {
        val removed = localItems.remove(idiotaLocal)
        if (removed) {
            // Guardar la lista local después de eliminar el elemento
            guardarListaLocal()
            // Notificar al adaptador sobre el cambio
            adapter.submitList(localItems.toList())
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == DetailLocal.DELETE_RESULT_CODE && resultCode == Activity.RESULT_OK) {
            val idiotaLocal = data?.getParcelableExtra<IdiotaLocal>("eliminarIdiotaLocal")
            idiotaLocal?.let {
                eliminarIdiotaLocal(it)
            }
        }
    }

    companion object {
        const val DELETE_RESULT_CODE = 1001
        val localItems = mutableListOf<IdiotaLocal>()
    }
}
