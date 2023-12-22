package com.example.lahoradelidiota.localList

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.example.lahoradelidiota.databinding.ActivityAddLocalBinding
import java.io.File
import java.util.*

class AddLocal : AppCompatActivity() {

    private lateinit var binding: ActivityAddLocalBinding
    private var selectedImageUri: Uri? = null
    private val PICK_IMAGE_REQUEST = 1
    private var copiedImageUri: Uri? = null

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

        // Verificar si algún campo está vacío
        if (numeroDeIdiota.isEmpty() || nombre.isEmpty() || nivel.isEmpty() || site.isEmpty() || habilidadEspecial.isEmpty() || descripcion.isEmpty()) {
            mostrarMensaje("Por favor, completa todos los campos.")
            return
        }

        // Copiar la imagen a la ubicación de tu aplicación
        copiedImageUri = copyImageToAppLocation(selectedImageUri)

        // Aquí puedes realizar la lógica para almacenar el ítem localmente
        val nuevoIdiota = IdiotaLocal(
            imagenUri = copiedImageUri,
            numeroDeIdiota = numeroDeIdiota,
            nombre = nombre,
            nivel = nivel,
            site = site,
            habilidadEspecial = habilidadEspecial,
            descripcion = descripcion
        )

        // Enviar el nuevo idiota de vuelta a la actividad anterior
        val resultIntent = Intent()
        resultIntent.putExtra("nuevoIdiota", nuevoIdiota)
        setResult(Activity.RESULT_OK, resultIntent)

        // Cerrar la actividad después de agregar el ítem
        finish()
    }

    private fun copyImageToAppLocation(originalUri: Uri?): Uri? {
        if (originalUri == null) {
            return null
        }

        val originalInputStream = contentResolver.openInputStream(originalUri)
        val destinationFile = File(getExternalFilesDir(null), "copied_image.jpg")
        destinationFile.outputStream().use { originalInputStream?.copyTo(it) }

        // Devolver la nueva URI después de copiar la imagen
        return FileProvider.getUriForFile(this, "com.example.lahoradelidiota.fileprovider", destinationFile)
    }

    private fun mostrarMensaje(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            selectedImageUri = data?.data
            binding.insertImage.setImageURI(selectedImageUri)
        }
    }
}

