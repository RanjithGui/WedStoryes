
package com.example.wedstoryes.customcomposables

import androidx.annotation.OptIn
import androidx.annotation.RawRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView

@OptIn(UnstableApi::class)
@Composable
fun CustomVideoPlayer(
    @RawRes videoRes: Int,
    videoPlayerViewModel: VideoPlayerViewModel = viewModel()
) {
    val context = LocalContext.current
    val player: ExoPlayer = videoPlayerViewModel.exoPlayer

    player.repeatMode = Player.REPEAT_MODE_ONE

    LaunchedEffect(videoRes) {
        videoPlayerViewModel.playVideo(videoRes)
    }

    var playerView: PlayerView? = null

    AndroidView(
        factory = {
            PlayerView(context).apply {
                useController = false
                resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM
                this.player = player
                playerView = this

            }
        },
        modifier = Modifier.fillMaxSize(),
        update = {
            it.player = player
            playerView = it
        }
    )

    DisposableEffect(Unit) {
        onDispose {
            player.pause()
            player.clearVideoSurface()
            playerView?.player = null
        }
    }
}
