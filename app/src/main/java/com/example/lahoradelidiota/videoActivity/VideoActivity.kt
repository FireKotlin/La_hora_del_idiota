package com.example.lahoradelidiota.videoActivity

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.lahoradelidiota.R
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.PlayerView
import com.google.firebase.firestore.FirebaseFirestore

class VideoActivity : AppCompatActivity() {

    private lateinit var playerView: PlayerView
    private lateinit var player: SimpleExoPlayer
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video)

        playerView = findViewById(R.id.playerView)
        player = SimpleExoPlayer.Builder(this).build()

        fetchVideoData()

        playerView.player = player
    }

    private fun fetchVideoData() {
        firestore.collection("Video").get()
            .addOnSuccessListener { documents ->
                val videoItems = mutableListOf<VideoItem>()

                for (document in documents) {
                    val numero = document.getLong("numero")?.toInt() ?: 0
                    val url = document.getString("url") ?: ""

                    videoItems.add(VideoItem(numero, url))
                }

                updatePlaylist(videoItems)
            }
            .addOnFailureListener { exception ->
                // Manejar errores
            }
    }

    private fun updatePlaylist(videoItems: List<VideoItem>) {
        val mediaItems = videoItems
            .sortedBy { it.numero }
            .map { MediaItem.fromUri(Uri.parse(it.url)) }

        player.clearMediaItems()
        player.addMediaItems(mediaItems)
        player.prepare()
        player.play()
    }

    override fun onDestroy() {
        super.onDestroy()
        player.release()
    }
}

