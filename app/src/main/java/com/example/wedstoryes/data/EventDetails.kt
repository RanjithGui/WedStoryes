package com.example.wedstoryes.data

data class EventDetails(
    val photographers: List<Photographers>?=null,
    val videographers: List<Videographers>?=null,
    val addons: List<Addons> = emptyList(),
)