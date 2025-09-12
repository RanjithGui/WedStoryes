package com.example.wedstoryes.data

data class EventDetails(
    val photographers: Photographers?=null,
    val videographers: Videographers?=null,
    val addons: List<Addons> = emptyList(),
)