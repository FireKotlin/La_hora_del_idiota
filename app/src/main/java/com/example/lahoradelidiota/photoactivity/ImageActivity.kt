package com.example.lahoradelidiota.photoactivity

import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.lahoradelidiota.R
import com.example.lahoradelidiota.databinding.ActivityImageBinding
import com.example.lahoradelidiota.main.MainActivity
import com.example.lahoradelidiota.photoAdapter
import com.google.firebase.firestore.FirebaseFirestore


class ImageActivity : AppCompatActivity() {

    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private lateinit var binding: ActivityImageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImageBinding.inflate(layoutInflater) // Asignar a la propiedad
        setContentView(binding.root)

        binding.recyclerView.layoutManager = GridLayoutManager(applicationContext, 2)

        val imageList = mutableListOf<image>()

        val adapter = photoAdapter()
        binding.recyclerView.adapter = adapter
        adapter.setDataList(imageList)

        val itoolbar = binding.imagetoolbar
        itoolbar.setNavigationIcon(R.drawable.baseline_arrow_back_24)
        itoolbar.setNavigationOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        fetchImageData(imageList, adapter)
    }

    private fun fetchImageData(imageList: MutableList<image>, adapter: photoAdapter) {
        firestore.collection("image").get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val number = document.getLong("number")?.toInt() ?: 0
                    val url = document.getString("url") ?: ""
                    val description = document.getString("description") ?: ""

                    val imageItem = image(number, url, description)
                    imageList.add(imageItem)
                }

                imageList.sortBy { it.number }
                adapter.setDataList(imageList)
            }
    }
}
