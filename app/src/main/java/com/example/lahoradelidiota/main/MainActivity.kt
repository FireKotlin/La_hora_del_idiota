package com.example.lahoradelidiota.main

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lahoradelidiota.detail.IDetailActivity
import com.example.lahoradelidiota.others.Idiota
import com.example.lahoradelidiota.others.IdiotaAdapter
import com.example.lahoradelidiota.R
import com.example.lahoradelidiota.R.color.nav2
import com.example.lahoradelidiota.videoActivity.VideoActivity
import com.example.lahoradelidiota.database.DbIdiotRecycler
import com.example.lahoradelidiota.database.LoginActivity
import com.example.lahoradelidiota.database.PantallaIdiota
import com.example.lahoradelidiota.databinding.ActivityMainBinding
import com.example.lahoradelidiota.photoactivity.ImageActivity
import com.google.android.material.navigation.NavigationView
import com.google.firebase.firestore.FirebaseFirestore
import java.util.Locale


class MainActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private var idiotList = mutableListOf<Idiota>()
    private lateinit var searchView: SearchView
    val adapter = IdiotaAdapter()

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {

        Thread.sleep(700)
        setTheme(R.style.Theme_LaHoraDelIdiota)

        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        binding.recyclerIdiot.layoutManager = LinearLayoutManager(this)

        val firestore = FirebaseFirestore.getInstance()
        val collectionReference = firestore.collection("idiotas")

        binding.recyclerIdiot.adapter = adapter
        adapter.submitList(idiotList)
        adapter.notifyDataSetChanged()

        adapter.setOnItemClickListener {
            openDetailActivity(it)
        }

        binding.fab.setOnClickListener {
            val intent = Intent(this, ImageActivity::class.java)
            startActivity(intent)
        }

        setSupportActionBar(binding.maintoolbar)
        val mainToolbar = binding.maintoolbar
        mainToolbar.setNavigationIcon(R.drawable.baseline_menu_24)
        mainToolbar.setNavigationOnClickListener {

            drawerLayout.openDrawer(GravityCompat.START)
        }

        drawerLayout = binding.drawerLayout
        val navigationView: NavigationView = binding.navigationView
        val backgroundColor = ContextCompat.getColor(this, nav2)
        navigationView.setBackgroundColor(backgroundColor)

        navigationView.setNavigationItemSelectedListener { menuItem ->

            drawerLayout.closeDrawers()

            when (menuItem.itemId) {
                R.id.menu_option1 -> {
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                }

                R.id.menu_option2 -> {
                    val intent = Intent(this, PantallaIdiota::class.java)
                    startActivity(intent)
                }

                R.id.menu_option3 -> {
                    val intent = Intent(this, DbIdiotRecycler::class.java)
                    startActivity(intent)
                }

                R.id.videoBtn -> {
                    val intent = Intent(this, VideoActivity::class.java)
                    startActivity(intent)
                }
            }
            true
        }

        collectionReference.addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.e("MainActivity", "Error al obtener la lista de idiotas", e)
                return@addSnapshotListener
            }
            snapshot?.let { it ->
                idiotList.clear()
                for (document in it) {
                    val imagenUrl = document.getString("imagenUrl") ?: ""
                    val numeroDeIdiota = document.getString("numeroDeIdiota") ?: ""
                    val nombre = document.getString("nombre") ?: ""
                    val nivel = document.getString("nivel") ?: ""
                    val site = document.getString("site") ?: ""
                    val habilidadEspecial = document.getString("habilidad") ?: ""
                    val descripcion = document.getString("descripcion") ?: ""

                    val idiota = Idiota(
                        imagenUrl,
                        numeroDeIdiota,
                        nombre,
                        nivel,
                        site,
                        habilidadEspecial,
                        descripcion
                    )
                    idiotList.add(idiota)
                }
                idiotList.sortBy { it.numeroDeIdiota.toInt() }
                adapter.notifyDataSetChanged()
            }
        }

        searchView = binding.searchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // No necesitas hacer nada aquí, ya que se manejará en onQueryTextChange
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterIdiotList(newText)
                return true
            }
        })
    }
    private fun openDetailActivity(earthquake: Idiota) {
        val intent = Intent(this, IDetailActivity::class.java)
        intent.putExtra(IDetailActivity.IDIOT_KEY, earthquake)
        startActivity(intent)
    }
    private fun filterIdiotList(query: String?) {
        val filteredList = idiotList.filter {
            it.nombre.toLowerCase(Locale.getDefault()).contains(query?.toLowerCase(Locale.getDefault()) ?: "")
        }
        adapter.submitList(filteredList)
        adapter.notifyDataSetChanged()
    }
}
