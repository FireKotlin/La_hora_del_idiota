package com.example.lahoradelidiota.localList

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.viewModels
import com.example.lahoradelidiota.databinding.ActivityAddLocalBinding
import com.example.lahoradelidiota.localList.db.AppDatabase
import com.example.lahoradelidiota.localList.db.IdiotaRepository
import com.example.lahoradelidiota.localList.db.IdiotaViewModel
import com.example.lahoradelidiota.localList.db.IdiotaViewModelFactory
import java.io.File
import java.io.FileOutputStream

class AddLocal : AppCompatActivity() {

    private lateinit var binding: ActivityAddLocalBinding
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
    private var selectedImageUri: Uri? = null

    private val selectImageLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            selectedImageUri = result.data?.data
            binding.insertImage.setImageURI(selectedImageUri)
            Log.d("SelectImage", "URI seleccionada: $selectedImageUri")
        }
    }

    companion object {
        const val REQUEST_GALLERY = 101
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddLocalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupSaveButton()
        setupImageSelection()
    }

    private fun setupSaveButton() {
        binding.acceptButton.setOnClickListener {
            saveIdiotaLocal()
        }
    }

    private fun setupImageSelection() {
        binding.insertImage.setOnClickListener {
            openGallery()
        }
    }

    private fun saveIdiotaLocal() {
        val nombre = binding.nombreEdit.text.toString()
        val numeroDeIdiota = binding.numEdit.text.toString()
        val nivel = binding.nivelEdit.text.toString()
        val site = binding.sitedit.text.toString()
        val habilidadEspecial = binding.habilidadEdit.text.toString()
        val descripcion = binding.descripcionEdit.text.toString()

        val imagePath = selectedImageUri?.let { uri -> saveImageInInternalStorage(uri) }
        Log.d("SaveImage", "Guardando ruta de la imagen: $imagePath")

        val idiotaLocal = IdiotaLocal(

            imagenUri = imagePath ?: "",
            nombre = nombre,
            numeroDeIdiota = numeroDeIdiota,
            nivel = nivel,
            site = site,
            habilidadEspecial = habilidadEspecial,
            descripcion = descripcion
        )

        idiotaViewModel.insert(idiotaLocal)
        finish() // Cierra la actividad después de guardar
    }

    private fun openGallery() {
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        selectImageLauncher.launch(galleryIntent)
    }

    private fun saveImageInInternalStorage(uri: Uri): String {
        val inputStream = contentResolver.openInputStream(uri)
        val bitmap = BitmapFactory.decodeStream(inputStream)
        inputStream?.close()

        val filename = "imagen_${System.currentTimeMillis()}.jpg"
        val file = File(applicationContext.filesDir, filename)
        val outputStream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        outputStream.flush()
        outputStream.close()

        return file.absolutePath
    }
}