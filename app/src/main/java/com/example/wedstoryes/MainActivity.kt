package com.example.wedstoryes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.wedstoryes.navigation.Route
import com.example.wedstoryes.presentation.EventType
import com.example.wedstoryes.presentation.GlobalViewmodel
import com.example.wedstoryes.presentation.HomeScreen
import com.example.wedstoryes.presentation.SplashScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen().setKeepOnScreenCondition { false }
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        setContent {

            val nav = rememberNavController()
           val viewmodel: GlobalViewmodel = androidx.lifecycle.viewmodel.compose.viewModel()
            NavHost(navController = nav, startDestination = Route.Splash.value) {
                composable(Route.Splash.value) {
                    SplashScreen(
                        onFinished = {
                            nav.navigate(Route.Welcome.value) {
                                popUpTo(Route.Splash.value) { inclusive = true }
                            }
                        }
                    )
                }
                composable(Route.Welcome.value) { HomeScreen(getStarted = {
                    nav.navigate(Route.Gallery.value)
                }) }

                composable(Route.Gallery.value) { EventType(viewmodel,viewmodel::onEvent) }
        }
    }
}

}

