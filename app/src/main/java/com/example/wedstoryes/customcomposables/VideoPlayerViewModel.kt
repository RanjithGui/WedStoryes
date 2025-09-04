package com.example.wedstoryes.customcomposables

import android.app.Application
import androidx.core.net.toUri
import androidx.lifecycle.AndroidViewModel
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer

class VideoPlayerViewModel(application: Application) : AndroidViewModel(application) {

    private var _exoPlayer: ExoPlayer? = null
    val exoPlayer: ExoPlayer
        get() = _exoPlayer ?: buildPlayer().also { _exoPlayer = it }

    private fun buildPlayer(): ExoPlayer {
        return ExoPlayer.Builder(getApplication()).build()
    }

    fun playVideo(resId: Int) {
        val context = getApplication<Application>()
        val uri = "android.resource://${context.packageName}/$resId".toUri()
        val mediaItem = MediaItem.fromUri(uri)
        exoPlayer.setMediaItem(mediaItem)
        exoPlayer.prepare()
        exoPlayer.volume = 0f // Mute the video
        exoPlayer.playWhenReady = true
    }


    fun pauseVideo() {
        exoPlayer.playWhenReady = false
    }

    override fun onCleared() {
        super.onCleared()
        _exoPlayer?.release()
        _exoPlayer = null
    }
}
