package com.example.lahoradelidiota.detail

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.MotionEvent
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.widget.TextView
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import com.example.lahoradelidiota.R
import com.example.lahoradelidiota.databinding.ActivityMainBinding
import com.example.lahoradelidiota.others.Idiota
import com.example.lahoradelidiota.databinding.DetailActivityBinding
import com.example.lahoradelidiota.main.MainActivity
import com.google.android.material.snackbar.Snackbar

@Suppress("DEPRECATION")
class IDetailActivity : AppCompatActivity() {

    private lateinit var handler: Handler
    private lateinit var hideFabRunnable: Runnable
    private lateinit var binding: DetailActivityBinding
    private var fabHidden = false
    private var isMenuOpen = false

    companion object {
        const val IDIOT_KEY = "idiota"

    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DetailActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        handler = Handler()
        hideFabRunnable = Runnable {
            if (!fabHidden) {
                // Oculta el FAB si no está oculto
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
                    handler.postDelayed(hideFabRunnable, 1000) // Oculta el FAB después de 1 segundo
                }
            }
            false
        }

        // Inicia el temporizador de ocultar el FAB al iniciar la actividad
        handler.postDelayed(hideFabRunnable, 1000) // Oculta el FAB después de 1 segundo

        // Mostrar el FAB inicialmente
        binding.extendedFab.visibility = View.VISIBLE

        binding.extendedFab.setOnClickListener {
            toggleMenu()
        }

        binding.buttonOption1.setOnClickListener {
            // Acción del primer botón
            showSnackbar("Primer botón presionado")
        }

        binding.buttonOption2.setOnClickListener {
            // Acción del segundo botón
            showSnackbar("Segundo botón presionado")
        }


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


    }
    override fun onPause() {
        super.onPause()
        // Detiene el temporizador de ocultar el FAB al pausar la actividad
        handler.removeCallbacks(hideFabRunnable)
    }

    private fun fadeOutFab(extendedFab: View) {
        ViewCompat.animate(extendedFab)
            .alpha(0f)
            .withEndAction { extendedFab.visibility = View.GONE }
            .start()
    }

    private fun toggleMenu() {
        isMenuOpen = !isMenuOpen
        if (isMenuOpen) {
            binding.buttonOption1.visibility = View.VISIBLE
            binding.buttonOption2.visibility = View.VISIBLE
        } else {
            binding.buttonOption1.visibility = View.GONE
            binding.buttonOption2.visibility = View.GONE
        }
    }

    private fun showSnackbar(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }

}

