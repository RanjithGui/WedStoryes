package com.example.wedstoryes.navigation

// navigation/Route.kt
sealed class Route(val value: String) {
    data object Splash : Route("splash")
    data object Welcome : Route("welcome")
    data object Gallery : Route("gallery")
    data object EventDetails : Route("eventdetails")

    data object CustomerDetails : Route("customerdetails")

}
