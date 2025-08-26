package com.example.wedstoryes.presentation

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.wedstoryes.R
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(onFinished: () -> Unit) {
    val scale = remember { Animatable(0.8f) }
    var alpha = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        alpha.animateTo(1f, tween(600))
        scale.animateTo(1f, tween(900, easing = { OvershootInterpolator(2f).getInterpolation(it) }))
    }
    LaunchedEffect(Unit) {
        delay(3000L)
        onFinished() }

    Box(Modifier.fillMaxSize().background(Color.Transparent), contentAlignment = Alignment.Center) {
        Image(
            painter = painterResource(R.drawable.logo), // PNG with transparency
            contentDescription = null,
            modifier = Modifier
                .size(240.dp)
                .graphicsLayer { scaleX = scale.value; scaleY = scale.value;}
        )
    }
}
