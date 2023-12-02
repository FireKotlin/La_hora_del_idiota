package com.example.lahoradelidiota.localList

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.lahoradelidiota.databinding.ActivityAddLocalBinding
import android.app.Activity

class AddLocal : AppCompatActivity() {

    private lateinit var binding: ActivityAddLocalBinding
    private var selectedImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddLocalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.insertImage.setOnClickListener {
            openGallery()
        }

        binding.acceptButton.setOnClickListener {
            saveIdiota()
        }
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    private fun saveIdiota() {
        val numeroDeIdiota = binding.numEdit.text.toString()
        val nombre = binding.nombreEdit.text.toString()
        val nivel = binding.nivelEdit.text.toString()
        val site = binding.sitedit.text.toString()
        val habilidadEspecial = binding.habilidadEdit.text.toString()
        val descripcion = binding.descripcionEdit.text.toString()

        val nuevoIdiota = IdiotaLocal(
            imagenUriString = selectedImageUri.toString(),
            numeroDeIdiota = numeroDeIdiota,
            nombre = nombre,
            nivel = nivel,
            site = site,
            habilidadEspecial = habilidadEspecial,
            descripcion = descripcion
        )

        val intent = Intent()
        intent.putExtra("nuevoIdiota", nuevoIdiota)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK) {
            selectedImageUri = data?.data
            binding.insertImage.setImageURI(selectedImageUri)
        }
    }

    companion object {
        private const val PICK_IMAGE_REQUEST = 1
    }
}