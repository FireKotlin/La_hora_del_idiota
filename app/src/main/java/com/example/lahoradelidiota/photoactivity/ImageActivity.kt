package com.example.lahoradelidiota.photoactivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.example.lahoradelidiota.R
import com.example.lahoradelidiota.databinding.ActivityImageBinding
import com.example.lahoradelidiota.login.LoginActivity
import com.example.lahoradelidiota.photoAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext


class ImageActivity : AppCompatActivity() {

    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private lateinit var binding: ActivityImageBinding
    private val adapter = photoAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (!isUserLoggedIn()) {
            Toast.makeText(this, "Inicia sesión para desbloquear!!!", Toast.LENGTH_LONG).show()
             val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
            return
        }

        binding = ActivityImageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        setupToolbar()
        fetchImageData()
    }

    private fun setupRecyclerView() {
        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(applicationContext, 2)
            adapter = this@ImageActivity.adapter
        }
    }

    private fun setupToolbar() {
        binding.imagetoolbar.apply {
            setNavigationIcon(R.drawable.baseline_arrow_back_24)
            setNavigationOnClickListener { onBackPressed() }
        }
    }
    private fun fetchImageData() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val documents = firestore.collection("image").get().await()
                val sortedImageList = documents.mapNotNull { document ->
                    createImageFromDocument(document)
                }.sortedBy { it.number }.toMutableList()

                withContext(Dispatchers.Main) {
                    adapter.setDataList(sortedImageList)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Log.e("ImageActivity", "Error fetching images", e)
                }
            }
        }
    }
    private fun createImageFromDocument(document: DocumentSnapshot): image? {
        val number = document.getLong("number")?.toInt() ?: return null
        val url = document.getString("url") ?: return null
        val description = document.getString("description") ?: return null

        return image(number, url, description)
    }
    private fun isUserLoggedIn(): Boolean {
        // Verifica si hay un usuario autenticado actualmente
        return FirebaseAuth.getInstance().currentUser != null
    }
}
