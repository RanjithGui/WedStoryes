package com.example.wedstoryes.data

data class EventDetails(
    val photographers: Photographers,
    val videographers: Videographers,
    val addons: List<Addons> = emptyList(),
)