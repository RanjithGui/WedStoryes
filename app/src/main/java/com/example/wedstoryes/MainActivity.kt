package com.example.wedstoryes

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.wedstoryes.navigation.Route
import com.example.wedstoryes.presentation.ClientDetailsScreen
import com.example.wedstoryes.presentation.EventDetailsScreen
import com.example.wedstoryes.presentation.EventType
import com.example.wedstoryes.presentation.GlobalViewmodel
import com.example.wedstoryes.presentation.HomeScreen
import com.example.wedstoryes.presentation.PdfQuotationScreen
import com.example.wedstoryes.presentation.SplashScreen
import com.example.wedstoryes.ui.theme.WedStoryesTheme

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen().setKeepOnScreenCondition { false }
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        setContent {
            WedStoryesTheme {
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
                    composable(Route.Welcome.value) {
                        HomeScreen(getStarted = {
                            nav.navigate(Route.Gallery.value)
                        })
                    }

                    composable(Route.Gallery.value) {
                        EventType(viewmodel, viewmodel::onEvent, navController = nav, onProceed = {
                            nav.navigate(Route.EventDetails.value)
                        })
                    }

                    composable(Route.EventDetails.value) {
                        EventDetailsScreen(viewmodel, viewmodel::onEvent, navController = nav,onContinue = {
                            nav.navigate(Route.CustomerDetails.value)
                        })
                    }

                    composable(Route.CustomerDetails.value){
                       ClientDetailsScreen(viewmodel, viewmodel::onEvent, navController = nav,onContinue = {
                           nav.navigate(Route.PdfQuotation.value)
                       })
                    }
                    composable (Route.PdfQuotation.value){
                        PdfQuotationScreen(viewmodel = viewmodel, navController = nav)
                    }

                }
            }
        }

    }
}

