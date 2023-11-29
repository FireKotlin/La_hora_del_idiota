package com.example.lahoradelidiota.database

import android.content.pm.ActivityInfo
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.lahoradelidiota.R
import com.example.lahoradelidiota.databinding.ActivityPantallaIdiotaBinding

class PantallaIdiota : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityPantallaIdiotaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mediaPlayer = MediaPlayer.create(this, R.raw.idiotsound)

        binding.soundbton.setOnClickListener {
            mediaPlayer.start()
        }
    }
}