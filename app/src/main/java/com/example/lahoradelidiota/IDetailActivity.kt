package com.example.lahoradelidiota

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.lahoradelidiota.databinding.DetailActivityBinding

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

    }
}
