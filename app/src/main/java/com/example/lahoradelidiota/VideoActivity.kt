package com.example.lahoradelidiota

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.PlayerView
class VideoActivity : AppCompatActivity() {

    private lateinit var playerView: PlayerView
    private lateinit var player: SimpleExoPlayer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video)

        playerView = findViewById(R.id.playerView)
        player = SimpleExoPlayer.Builder(this).build()

        val videoUrls = listOf(
            "https://firebasestorage.googleapis.com/v0/b/la-hora-del-idiota.appspot.com/o/Videos%2FWhatsApp%20Video%202023-11-09%20at%209.58.01%20PM.mp4?alt=media&token=ee371f01-b2d4-4ac0-9ca6-ce5161da4018",
            "https://firebasestorage.googleapis.com/v0/b/la-hora-del-idiota.appspot.com/o/Videos%2FWhatsApp%20Video%202023-08-07%20at%2010.05.42%20PM.mp4?alt=media&token=5cb12864-1cea-49d9-8b42-aba9f399c901",
            "https://firebasestorage.googleapis.com/v0/b/la-hora-del-idiota.appspot.com/o/Videos%2FWhatsApp%20Video%202023-07-26%20at%201.37.47%20AM.mp4?alt=media&token=4c203fcf-377a-459e-ac15-5c879a5a5ea6"
        )
        val mediaItems = videoUrls.map { MediaItem.fromUri(Uri.parse(it)) }
        player.addMediaItems(mediaItems)
        player.prepare()
        player.play()

        playerView.player = player
    }
    override fun onDestroy() {
        super.onDestroy()
        player.release()
    }
}