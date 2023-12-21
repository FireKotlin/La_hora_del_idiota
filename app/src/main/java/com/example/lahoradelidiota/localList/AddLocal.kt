package com.example.lahoradelidiota.localList

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.lahoradelidiota.databinding.ActivityAddLocalBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID

class AddLocal : AppCompatActivity() {

    private lateinit var binding: ActivityAddLocalBinding
    private var selectedImageUri: Uri? = null
    private val PICK_IMAGE_REQUEST = 1

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

        val userId = FirebaseAuth.getInstance().currentUser?.uid
        if (userId != null) {
            val referenciaIdiotasLocales = FirebaseDatabase.getInstance().getReference("usuarios/$userId/idiotasLocales")

            if (selectedImageUri != null) {
                subirImagenFirebaseStorage(selectedImageUri!!) { urlDeDescarga ->
                    val nuevoIdiota = IdiotaLocal(
                        imagenUri = urlDeDescarga,
                        numeroDeIdiota = numeroDeIdiota,
                        nombre = nombre,
                        nivel = nivel,
                        site = site,
                        habilidadEspecial = habilidadEspecial,
                        descripcion = descripcion
                    )

                    referenciaIdiotasLocales.push().setValue(nuevoIdiota)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                mostrarMensaje("Idiota local agregado exitosamente.")
                                finish()
                            } else {
                                mostrarMensaje("Error al agregar el idiota local. Por favor, intenta nuevamente. Error: ${task.exception?.message}")
                            }
                        }
                }
            } else {
                val nuevoIdiota = IdiotaLocal(
                    imagenUri = null,
                    numeroDeIdiota = numeroDeIdiota,
                    nombre = nombre,
                    nivel = nivel,
                    site = site,
                    habilidadEspecial = habilidadEspecial,
                    descripcion = descripcion
                )

                referenciaIdiotasLocales.push().setValue(nuevoIdiota)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            mostrarMensaje("Idiota local agregado exitosamente.")
                            finish()
                        } else {
                            mostrarMensaje("Error al agregar el idiota local. Por favor, intenta nuevamente. Error: ${task.exception?.message}")
                        }
                    }
            }
        } else {
            // Manejar el caso en que el usuario no esté autenticado
            mostrarMensaje("Usuario no autenticado. Inicia sesión e intenta nuevamente.")
        }
    }

    private fun subirImagenFirebaseStorage(imagenUri: Uri, callback: (String) -> Unit) {
        val storageRef = FirebaseStorage.getInstance().reference
        val imagenRef = storageRef.child("imagenes/${UUID.randomUUID()}.jpg")

        imagenRef.putFile(imagenUri)
            .addOnSuccessListener { taskSnapshot ->
                // La imagen se subió exitosamente, obtener la URL de descarga
                imagenRef.downloadUrl.addOnSuccessListener { uri ->
                    val urlDeDescarga = uri.toString()
                    callback(urlDeDescarga)
                }
                    .addOnFailureListener { exception ->
                        // Manejar la falla al obtener la URL de descarga
                        mostrarMensaje("Error al obtener la URL de descarga. Por favor, intenta nuevamente.")
                    }
            }
            .addOnFailureListener { exception ->
                // Manejar la falla en la subida de la imagen
                mostrarMensaje("Error al subir la imagen. Por favor, intenta nuevamente. Error: ${exception.message}")
            }
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

