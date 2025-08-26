package com.example.wedstoryes.presentation

import android.net.Uri
import android.os.Build
import android.widget.Button
import android.widget.FrameLayout
import androidx.annotation.OptIn
import androidx.annotation.RawRes
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import com.example.wedstoryes.R
import androidx.core.net.toUri

@Composable
fun HomeScreen(getStarted: () -> Unit) {
    var jump by remember { mutableStateOf(false) }
    var jump2 by remember { mutableStateOf(false) }
    var jump3 by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        while (true) {
            jump = true
            kotlinx.coroutines.delay(400)
            jump = false
            jump2= true
            kotlinx.coroutines.delay(400)
            jump2 = false
            jump3= true
            kotlinx.coroutines.delay(400)
            jump3 = false
        }
    }

    Box(modifier = Modifier.fillMaxSize().background(color = colorResource(R.color.teal_200)) ){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            ExoVideoBackground(videoRes = R.raw.video_background)
        }
        Column (modifier = Modifier
            .fillMaxSize().padding(16.dp)){
            Image(
                painter = painterResource(R.drawable.logo),
                contentDescription = null,
                modifier = Modifier
                    .size(240.dp)
                    .align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.size(24.dp))
            Row(modifier = Modifier.fillMaxWidth(),Arrangement.Center) {
                val offsetY by animateDpAsState(
                    targetValue = if (jump) (-18).dp else 0.dp,
                    label = "jump"
                )
                val offsetY2 by animateDpAsState(
                    targetValue = if (jump2) (-18).dp else 0.dp,
                    label = "jump"
                )
                val offsetY3 by animateDpAsState(
                    targetValue = if (jump3) (-18).dp else 0.dp,
                    label = "jump"
                )
                Image(painter = painterResource(R.drawable.camera), contentDescription = null, modifier = Modifier.size(44.dp).padding(end = 15.dp).offset(y = offsetY))
                Image(painter = painterResource(R.drawable.heart_icon), contentDescription = null, modifier = Modifier.size(28.dp).offset(y = offsetY2))
                Image(painter = painterResource(R.drawable.outline_videocam_24), contentDescription = null, modifier = Modifier.size(44.dp).padding(start = 15.dp).offset(y = offsetY3))


            }
            Text(text = "Capture Your Perfect Moments", modifier = Modifier
                .wrapContentSize()
                .align(Alignment.CenterHorizontally), color = colorResource( R.color.wedstoreys), fontSize = 24.sp, fontWeight = FontWeight.Bold,textAlign = TextAlign.Center)
            Spacer(modifier = Modifier.size(8.dp))
            Text(text =
                "Professional photography and videography services for weddings, events, and special occasions.", modifier = Modifier
                .fillMaxWidth()
                .align(alignment = Alignment.CenterHorizontally), color = Color.White, textAlign = TextAlign.Center,fontWeight = FontWeight.SemiBold)
           Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter){
               androidx.compose.material3.Button(
                   onClick = { getStarted() },
                   modifier = Modifier
                       .padding( start = 16.dp, end = 16.dp, bottom = 32.dp).fillMaxWidth(),
                   colors = ButtonColors(containerColor = colorResource(R.color.wedstoreys),
                       contentColor = Color.White, disabledContainerColor = Color.Gray, disabledContentColor = Color.LightGray)
               ) {
                   Text(text = "Get Started")
               }
           }

        }
    }

}


@OptIn(UnstableApi::class)
@Composable
fun ExoVideoBackground(@RawRes videoRes: Int) {
    val context = LocalContext.current
    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            val uri = "android.resource://${context.packageName}/$videoRes".toUri()
            setMediaItem(MediaItem.fromUri(uri))
            repeatMode = Player.REPEAT_MODE_ALL
            prepare()
            playWhenReady = true
            volume = 0f
        }
    }

    AndroidView(
        factory = {
            PlayerView(context).apply {
                useController = false
                resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM // fills screen
                player = exoPlayer
            }
        },
        modifier = Modifier.fillMaxSize()
    )
}
