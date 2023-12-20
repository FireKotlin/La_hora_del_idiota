package com.example.lahoradelidiota.localList

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import com.example.lahoradelidiota.R
import com.example.lahoradelidiota.databinding.ActivityDetailLocalBinding

class DetailLocal : AppCompatActivity() {

    private lateinit var handler: Handler
    private lateinit var hideFabRunnable: Runnable
    private lateinit var binding: ActivityDetailLocalBinding
    private var fabHidden = false
    private var stopHideFabTimer = false
    private var clicked = false

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

        binding.scrollView.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> {
                    if (fabHidden) {
                        ViewCompat.animate(binding.extendedFab).cancel()
                        binding.extendedFab.alpha = 1f
                        binding.extendedFab.visibility = View.VISIBLE
                        fabHidden = false
                    }
                    handler.removeCallbacks(hideFabRunnable)
                    handler.postDelayed(hideFabRunnable, 6000)
                }
            }
            false
        }

        handler.postDelayed(hideFabRunnable, 6000)

        binding.extendedFab.visibility = View.VISIBLE
        binding.extendedFab1.visibility = View.GONE
        binding.extendedFab2.visibility = View.GONE

        val idiotaLocal: IdiotaLocal? = intent.getParcelableExtra("idiotaLocal")

        if (idiotaLocal != null) {
            val imagePath = idiotaLocal.imagenUri?.toString()
            if (imagePath != null) {
                val imageUri = Uri.parse(imagePath)
                binding.detailImage.setImageURI(imageUri)
            } else {
                // Manejar caso donde la ruta de la imagen es nula
            }
            binding.detailName.text = idiotaLocal.nombre
            binding.nivelDeIdiotes.text = idiotaLocal.nivel
            binding.sitioFrecuente.text = idiotaLocal.site
            binding.habilidadEspecial.text = idiotaLocal.habilidadEspecial
            binding.descripcion.text = idiotaLocal.descripcion
        }

        val toolbar = binding.detailtoolbar
        toolbar.title = idiotaLocal?.nombre ?: ""
        toolbar.setNavigationIcon(R.drawable.baseline_arrow_back_24)
        toolbar.setNavigationOnClickListener {
            startActivity(Intent(this, LocalList::class.java))
        }

        binding.extendedFab.setOnClickListener {
            stopHideFabTimer = true
            onAddButtonClicked()
        }
        binding.extendedFab1.setOnClickListener {
            // Lógica para guardar imagen en la galería
        }
        binding.extendedFab2.setOnClickListener {
            // Lógica para compartir captura de pantalla
        }
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

    private fun onAddButtonClicked() {
        setVisibility(clicked)
        setAnimation(clicked)
        clicked = !clicked

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
}

