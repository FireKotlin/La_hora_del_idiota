package com.example.lahoradelidiota.localList

import android.Manifest
import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.example.lahoradelidiota.R
import com.example.lahoradelidiota.databinding.ActivityDetailLocalBinding
import com.example.lahoradelidiota.localList.db.AppDatabase
import com.example.lahoradelidiota.localList.db.IdiotaRepository
import com.example.lahoradelidiota.localList.db.IdiotaViewModel
import com.example.lahoradelidiota.localList.db.IdiotaViewModelFactory
import java.io.File

class DetailLocal : AppCompatActivity() {

    private lateinit var handler: Handler
    private lateinit var hideFabRunnable: Runnable
    private lateinit var binding: ActivityDetailLocalBinding
    private var fabHidden = false
    private var stopHideFabTimer = false
    private var clicked = false

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

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailLocalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val idiotaId = intent.getLongExtra("IDIOTA_ID", -1)
        if (idiotaId != -1L) {
            idiotaViewModel.getIdiotaById(idiotaId).observe(this) { idiotaLocal ->
                displayIdiotaDetails(idiotaLocal)
            }
        } else {
            Toast.makeText(this, "Error: No se pudo cargar los detalles.", Toast.LENGTH_LONG).show()
        }
    }
    private fun displayIdiotaDetails(idiotaLocal: IdiotaLocal) {
        Log.d("LoadImage", "Recuperando ruta de la imagen: ${idiotaLocal.imagenUri}")

        binding.detailName.text = idiotaLocal.nombre
        binding.nivelDeIdiotes.text = idiotaLocal.nivel
        binding.sitioFrecuente.text = idiotaLocal.site
        binding.habilidadEspecial.text = idiotaLocal.habilidadEspecial
        binding.descripcion.text = idiotaLocal.descripcion

        idiotaLocal.imagenUri?.let { loadImagen(it) }
    }

    private fun loadImagen(rutaArchivo: String) {
        val file = File(rutaArchivo)
        if (file.exists()) {
            Glide.with(this)
                .load(file)
                .error(R.drawable.p31) // Reemplaza con tu imagen de error
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(e: GlideException?, model: Any?, target: com.bumptech.glide.request.target.Target<Drawable>?, isFirstResource: Boolean): Boolean {
                        Log.e("Glide", "Error al cargar la imagen", e)
                        return false
                    }

                    override fun onResourceReady(resource: Drawable?, model: Any?, target: com.bumptech.glide.request.target.Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                        return false
                    }
                })
                .into(binding.detailImage)
        } else {
            Log.e("LoadImage", "El archivo no existe: $rutaArchivo")
            // Aquí puedes manejar el caso de que el archivo no exista
        }

    }
    private fun setupFabButtons() {
        binding.extendedFab.setOnClickListener {
            stopHideFabTimer = true
            onAddButtonClicked()
        }
        binding.extendedFab1.setOnClickListener {
        }
        binding.extendedFab2.setOnClickListener {
            // Share logic...
        }
        binding.extendedFab3.setOnClickListener {
        }
    }


    private fun onAddButtonClicked() {
        setVisibility(clicked)
        setAnimation(clicked)
        clicked = !clicked
        fadeOutFab(binding.extendedFab1)
        fadeOutFab(binding.extendedFab2)
        fadeOutFab(binding.extendedFab3)
    }

    private fun fadeOutFab(extendedFab: View) {
        if (!stopHideFabTimer && !fabHidden) {
            ViewCompat.animate(extendedFab)
                .alpha(0f)
                .withEndAction {
                    extendedFab.visibility = View.GONE
                    stopHideFabTimer = false
                }
                .start()
        }
    }
    private fun setAnimation(clicked: Boolean) {
        if (!clicked) {
            binding.extendedFab1.apply {
                visibility = View.VISIBLE
                translationY = 0f
            }

            binding.extendedFab2.apply {
                visibility = View.VISIBLE
                translationY = 0f
            }

            binding.extendedFab.apply {
                rotation = 0f
            }
        } else {
            binding.extendedFab1.apply {
                translationY = 300f
            }

            binding.extendedFab2.apply {
                translationY = 600f
            }

            binding.extendedFab.apply {
                rotation = 180f
            }
        }
    }
    private fun setVisibility(clicked: Boolean) {
        if (!clicked) {
            binding.extendedFab1.visibility = View.VISIBLE
            binding.extendedFab2.visibility = View.VISIBLE
            binding.extendedFab3.visibility = View.VISIBLE
        } else {
            binding.extendedFab1.visibility = View.INVISIBLE
            binding.extendedFab2.visibility = View.INVISIBLE
            binding.extendedFab3.visibility = View.INVISIBLE
        }
    }
}