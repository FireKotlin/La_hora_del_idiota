package com.example.lahoradelidiota.localList

import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.core.graphics.drawable.toBitmap
import androidx.core.view.ViewCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.example.lahoradelidiota.BuildConfig
import com.example.lahoradelidiota.R
import com.example.lahoradelidiota.databinding.ActivityDetailLocalBinding
import com.example.lahoradelidiota.localList.db.AppDatabase
import com.example.lahoradelidiota.localList.db.IdiotaRepository
import com.example.lahoradelidiota.localList.db.IdiotaViewModel
import com.example.lahoradelidiota.localList.db.IdiotaViewModelFactory
import com.example.lahoradelidiota.main.MainActivity
import java.io.File
import java.io.FileOutputStream

class DetailLocal : AppCompatActivity() {

    private lateinit var handler: Handler
    private lateinit var hideFabRunnable: Runnable
    private lateinit var binding: ActivityDetailLocalBinding
    private var fabHidden = false
    private var stopHideFabTimer = false
    private var clicked = false
    private var idiotaLocalActual: IdiotaLocal? = null


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

        handler = Handler()
        hideFabRunnable = Runnable {
            if (!fabHidden && !stopHideFabTimer) {

                fadeOutFab(binding.extendedFab)
                fabHidden = true
            }
        }
        handler.postDelayed(hideFabRunnable, 4100)

        binding.extendedFab.visibility = View.VISIBLE
        binding.extendedFab1.visibility = View.GONE
        binding.extendedFab2.visibility = View.GONE
        binding.extendedFab3.visibility = View.GONE



        val idiotaId = intent.getLongExtra("IDIOTA_ID", -1)
        if (idiotaId != -1L) {
            idiotaViewModel.getIdiotaById(idiotaId).observe(this) { idiotaLocal ->
                // Verificar si idiotaLocal no es null antes de utilizarlo
                if (idiotaLocal != null) {
                    idiotaLocalActual = idiotaLocal
                    displayIdiotaDetails(idiotaLocal)
                }
            }
        } else {
            Toast.makeText(this, "Error: No se pudo cargar los detalles.", Toast.LENGTH_LONG).show()
        }


        binding.extendedFab.setOnClickListener {
            stopHideFabTimer = true
            onAddButtonClicked()
        }
        binding.extendedFab1.setOnClickListener {
            saveImageToGallery()
        }
        binding.extendedFab2.setOnClickListener {
            shareScreenshot()
        }
        binding.extendedFab3.setOnClickListener {
            idiotaLocalActual?.let { showDeleteConfirmationDialog(it)}
        }
    }

    private fun showDeleteConfirmationDialog(idiotaLocal: IdiotaLocal) {
        AlertDialog.Builder(this)
            .setTitle("Eliminar Idiota")
            .setMessage("¿Estás seguro de que quieres eliminar este idiota?")
            .setPositiveButton("Eliminar") { dialog, _ ->
                idiotaLocalActual?.let {
                    idiotaViewModel.deleteIdiota(it)
                    idiotaLocalActual = null // Establecer a null después de eliminar
                }
                dialog.dismiss()
                finish() // Regresa a la actividad anterior después de eliminar
            }
            .setNegativeButton("Cancelar") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }
    private fun displayIdiotaDetails(idiotaLocal: IdiotaLocal) {
        Log.d("LoadImage", "Recuperando ruta de la imagen: ${idiotaLocal.imagenUri}")

        binding.detailName.text = idiotaLocal.nombre
        binding.nivelDeIdiotes.text = idiotaLocal.nivel
        binding.sitioFrecuente.text = idiotaLocal.site
        binding.habilidadEspecial.text = idiotaLocal.habilidadEspecial
        binding.descripcion.text = idiotaLocal.descripcion

        idiotaLocal.imagenUri?.let { loadImagen(it) }
        val toolbar = binding.detailtoolbar
        toolbar.title = idiotaLocal.nombre
        toolbar.setNavigationIcon(R.drawable.baseline_arrow_back_24)
        toolbar.setNavigationOnClickListener {
            startActivity(Intent(this, LocalList::class.java))
        }
    }


    private fun loadImagen(rutaArchivo: String) {
        val file = File(rutaArchivo)
        if (file.exists()) {
            Glide.with(this)
                .load(file)
                .error(R.drawable.p31) // Reemplaza con tu imagen de error
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: com.bumptech.glide.request.target.Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        Log.e("Glide", "Error al cargar la imagen", e)
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: com.bumptech.glide.request.target.Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        return false
                    }
                })
                .into(binding.detailImage)
        } else {
            Log.e("LoadImage", "El archivo no existe: $rutaArchivo")
            // Aquí puedes manejar el caso de que el archivo no exista
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

    private fun saveImageToGallery() {
        val bitmap = binding.detailImage.drawable.toBitmap()
        val displayName = "my_image_${System.currentTimeMillis()}.jpg"

        val contentValues = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, displayName)
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
        }

        val resolver = contentResolver
        val uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

        if (uri != null) {
            resolver.openOutputStream(uri).use { outputStream ->
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream!!)
                Toast.makeText(this, "Image saved to gallery", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Failed to save image", Toast.LENGTH_SHORT).show()
        }
    }

    private fun shareScreenshot() {

        val bitmap = captureScrollViewContent()

        val width = bitmap.width
        val height = bitmap.height
        val whiteBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        whiteBitmap.eraseColor(android.graphics.Color.WHITE)
        val canvas = Canvas(whiteBitmap)
        canvas.drawBitmap(bitmap, 0f, 0f, null)

        val cachePath = File(this.cacheDir, "images")
        cachePath.mkdirs()
        val filePath = File(cachePath, "screenshot.png")
        val outputStream = FileOutputStream(filePath)
        whiteBitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        outputStream.close()

        val uri =
            FileProvider.getUriForFile(this, "${BuildConfig.APPLICATION_ID}.provider", filePath)

        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "image/*"
            putExtra(Intent.EXTRA_STREAM, uri)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }

        startActivity(Intent.createChooser(shareIntent, "Compartir captura de pantalla"))
    }

    private fun captureScrollViewContent(): Bitmap {

        val bitmap = Bitmap.createBitmap(
            binding.scrollView.width,
            binding.scrollView.getChildAt(0).height,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)

        binding.scrollView.draw(canvas)
        supportActionBar?.show()

        return bitmap
    }
}