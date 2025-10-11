package com.example.wedstoryes.data

data class SubEventDetails(
    val subEvent : String?=null,
    val photographers: List<Photographers>?=null,
    val videographers: List<Videographers>?=null,
    val date:String?=null, val time:String?=null,
    val addons: List<Addons>? = null,
)