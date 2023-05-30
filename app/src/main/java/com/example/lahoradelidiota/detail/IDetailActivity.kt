package com.example.lahoradelidiota.detail

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import com.example.lahoradelidiota.R
import com.example.lahoradelidiota.others.Idiota
import com.example.lahoradelidiota.databinding.DetailActivityBinding
import com.example.lahoradelidiota.main.MainActivity

@Suppress("DEPRECATION")
class IDetailActivity : AppCompatActivity() {

    companion object {
        const val IDIOT_KEY = "idiota"

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DetailActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
}

