package com.example.lahoradelidiota.detail

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.MotionEvent
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
import android.widget.Toast
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import com.example.lahoradelidiota.R
import com.example.lahoradelidiota.databinding.ActivityMainBinding
import com.example.lahoradelidiota.others.Idiota
import com.example.lahoradelidiota.databinding.DetailActivityBinding
import com.example.lahoradelidiota.main.MainActivity

@Suppress("DEPRECATION")
class IDetailActivity : AppCompatActivity() {

    // Ocultar FAB
    private lateinit var handler: Handler
    private lateinit var hideFabRunnable: Runnable
    private lateinit var binding: DetailActivityBinding
    private var fabHidden = false

    // Expandible
    private val rotateOpen: Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.rotate_open_anim) }
    private val rotateClose: Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.rotate_close_anim) }
    private val fromBottom: Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.from_bottom_anim) }
    private val toBottom: Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.to_bottom_anim) }

    private var clicked = false

    companion object {
        const val IDIOT_KEY = "idiota"
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DetailActivityBinding.inflate(layoutInflater)
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
                    handler.postDelayed(hideFabRunnable, 7000) // Oculta el FAB después de 1 segundo
                }
            }
            false
        }

        // Inicia el temporizador de ocultar el FAB al iniciar la actividad
        handler.postDelayed(hideFabRunnable, 7000) // Oculta el FAB después de 1 segundo

        // Mostrar el FAB inicialmente
        binding.extendedFab.visibility = View.VISIBLE

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
            onAddButtonClicked()
        }
        binding.extendedFab1.setOnClickListener {
            Toast.makeText(this, "Download Button Clicked", Toast.LENGTH_SHORT).show()
        }
        binding.extendedFab2.setOnClickListener {
            Toast.makeText(this, "Share Button Clicked", Toast.LENGTH_SHORT).show()
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

    private fun onAddButtonClicked() {
        setVisibility(clicked)
        setAnimation(clicked)
        clicked = !clicked
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
