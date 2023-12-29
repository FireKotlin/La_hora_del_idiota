package com.example.lahoradelidiota.photoactivity

import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import com.example.lahoradelidiota.R
import com.example.lahoradelidiota.databinding.ActivityImageBinding
import com.example.lahoradelidiota.main.MainActivity
import com.example.lahoradelidiota.photoAdapter
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore


class ImageActivity : AppCompatActivity() {

    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private lateinit var binding: ActivityImageBinding
    private val adapter = photoAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        firestore.collection("image").get()
            .addOnSuccessListener { documents ->
                val sortedImageList = documents.mapNotNull { document ->
                    createImageFromDocument(document)
                }.sortedBy { it.number }.toMutableList()

                adapter.setDataList(sortedImageList)
            }
            .addOnFailureListener { e ->

                Log.e("ImageActivity", "Error fetching images", e)
            }
    }


    private fun createImageFromDocument(document: DocumentSnapshot): image? {
        val number = document.getLong("number")?.toInt() ?: return null
        val url = document.getString("url") ?: return null
        val description = document.getString("description") ?: return null

        return image(number, url, description)
    }
}
