package com.example.wedstoryes.customcomposables

import androidx.annotation.OptIn
import androidx.annotation.RawRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView

@OptIn(UnstableApi::class)
@Composable
fun CustomVideoPlayer(@RawRes videoRes: Int,videoPlayerViewModel: VideoPlayerViewModel = viewModel()) {
    val context = LocalContext.current
    LaunchedEffect(videoRes) {
        videoPlayerViewModel.playVideo(videoRes)
    }
    AndroidView(
        factory = {
            PlayerView(context).apply {
                useController = false
                resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM // fills screen
                player = videoPlayerViewModel.exoPlayer
            }
        },
        modifier = Modifier.fillMaxSize()
    )
}