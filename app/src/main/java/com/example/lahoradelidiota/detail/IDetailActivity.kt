package com.example.lahoradelidiota.detail

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.provider.MediaStore
import android.view.MotionEvent
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.core.graphics.drawable.toBitmap
import androidx.core.view.ViewCompat
import com.example.lahoradelidiota.BuildConfig
import com.example.lahoradelidiota.R
import com.example.lahoradelidiota.others.Idiota
import com.example.lahoradelidiota.databinding.DetailActivityBinding
import com.example.lahoradelidiota.main.MainActivity
import java.io.File
import java.io.FileOutputStream

@Suppress("DEPRECATION")
class IDetailActivity : AppCompatActivity() {

    // Ocultar FAB
    private lateinit var handler: Handler
    private lateinit var hideFabRunnable: Runnable
    private lateinit var binding: DetailActivityBinding
    private var fabHidden = false

    // Agregar una variable para controlar el temporizador
    private var stopHideFabTimer = false

    // Expandible
    private val rotateOpen: Animation by lazy {
        AnimationUtils.loadAnimation(
            this,
            R.anim.rotate_open_anim
        )
    }
    private val rotateClose: Animation by lazy {
        AnimationUtils.loadAnimation(
            this,
            R.anim.rotate_close_anim
        )
    }
    private val fromBottom: Animation by lazy {
        AnimationUtils.loadAnimation(
            this,
            R.anim.from_bottom_anim
        )
    }
    private val toBottom: Animation by lazy {
        AnimationUtils.loadAnimation(
            this,
            R.anim.to_bottom_anim
        )
    }

    private var clicked = false

    companion object {
        private const val REQUEST_WRITE_STORAGE_PERMISSION = 101
        const val IDIOT_KEY = "idiota"
    }

    @RequiresApi(Build.VERSION_CODES.R)
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DetailActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        handler = Handler()
        hideFabRunnable = Runnable {
            if (!fabHidden && !stopHideFabTimer) {

                fadeOutFab(binding.extendedFab)
                fabHidden = true
            }
        }

        binding.scrollView.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> {
                    if (fabHidden) {
                        // Cancela la animación de desvanecimiento y muestra el FAB nuevamente
                        ViewCompat.animate(binding.extendedFab).cancel()
                        binding.extendedFab.alpha = 1f
                        binding.extendedFab.visibility = View.VISIBLE
                        fabHidden = false
                    }

                    // Reinicia el temporizador de ocultar el FAB
                    handler.removeCallbacks(hideFabRunnable)
                    handler.postDelayed(
                        hideFabRunnable,
                        6000)
                }
            }
            false
        }

        // Inicia el temporizador de ocultar el FAB al iniciar la actividad
        handler.postDelayed(hideFabRunnable, 6000) // Oculta el FAB después de 7 segundos

        // Mostrar el FAB inicialmente
        binding.extendedFab.visibility = View.VISIBLE
        binding.extendedFab1.visibility = View.GONE
        binding.extendedFab2.visibility = View.GONE

        val idiota = intent.extras?.getParcelable<Idiota>(IDIOT_KEY)!!

        binding.detailImage.setImageResource(idiota.Imagenid)
        binding.detailName.text = idiota.nombre
        binding.nivelDeIdiotes.text = idiota.nivel
        binding.sitioFrecuente.text = idiota.site
        binding.habilidadEspecial.text = idiota.habilidadEspecial
        binding.descripcion.text = idiota.descripcion

        val toolbar = binding.detailtoolbar
        toolbar.title = idiota.nombre
        toolbar.setNavigationIcon(R.drawable.baseline_arrow_back_24)
        toolbar.setNavigationOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }


        binding.extendedFab.setOnClickListener {
            // Detener el temporizador de ocultar el FAB
            stopHideFabTimer = true
            onAddButtonClicked()
        }
        binding.extendedFab1.setOnClickListener {
            saveImageToGallery()
        }
        binding.extendedFab2.setOnClickListener {
            shareScreenshot()
        }
    }

    override fun onPause() {
        super.onPause()
        // Detener el temporizador de ocultar el FAB al pausar la actividad
        handler.removeCallbacks(hideFabRunnable)
    }

    private fun fadeOutFab(extendedFab: View) {
        if (!stopHideFabTimer && !fabHidden) {
            ViewCompat.animate(extendedFab)
                .alpha(0f)
                .withEndAction {
                    extendedFab.visibility = View.GONE
                    stopHideFabTimer = false // Restablecer el temporizador cuando se oculte el FAB
                }
                .start()
        }
    }

    private fun onAddButtonClicked() {
        setVisibility(clicked)
        setAnimation(clicked)
        clicked = !clicked

        // Oculta el botón principal si ambos botones adicionales también están ocultos
        if (binding.extendedFab1.visibility == View.INVISIBLE && binding.extendedFab2.visibility == View.INVISIBLE) {
            fabHidden = true
            fadeOutFab(binding.extendedFab)
        } else {
            fabHidden = false
        }
    }

    private fun setVisibility(clicked: Boolean) {
        if (!clicked) {
            binding.extendedFab1.visibility = View.VISIBLE
            binding.extendedFab2.visibility = View.VISIBLE
        } else {
            binding.extendedFab1.visibility = View.INVISIBLE
            binding.extendedFab2.visibility = View.INVISIBLE
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

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_WRITE_STORAGE_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                saveImageToGallery()
            } else {
                Toast.makeText(
                    this,
                    "Permission denied. Cannot save image to gallery.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun captureScrollViewContent(): Bitmap {
        // Capturar la vista completa en un Bitmap
        val bitmap = Bitmap.createBitmap(binding.scrollView.width, binding.scrollView.getChildAt(0).height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        binding.scrollView.draw(canvas)

        supportActionBar?.show()

        return bitmap
    }

    private fun shareScreenshot() {
        // Capturar la vista completa en un Bitmap
        val bitmap = captureScrollViewContent()

        // Añadir fondo blanco alrededor del Bitmap
        val width = bitmap.width
        val height = bitmap.height
        val whiteBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        whiteBitmap.eraseColor(android.graphics.Color.WHITE)
        val canvas = Canvas(whiteBitmap)
        canvas.drawBitmap(bitmap, 0f, 0f, null)

        // Guardar el Bitmap en un archivo temporal
        val cachePath = File(this.cacheDir, "images")
        cachePath.mkdirs()
        val filePath = File(cachePath, "screenshot.png")
        val outputStream = FileOutputStream(filePath)
        whiteBitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        outputStream.close()

        // Crear un URI para el archivo temporal
        val uri = FileProvider.getUriForFile(this, "${BuildConfig.APPLICATION_ID}.provider", filePath)

        // Crear un Intent para compartir la imagen
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "image/*"
            putExtra(Intent.EXTRA_STREAM, uri)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }

        // Iniciar el Intent para compartir
        startActivity(Intent.createChooser(shareIntent, "Compartir captura de pantalla"))
    }
}