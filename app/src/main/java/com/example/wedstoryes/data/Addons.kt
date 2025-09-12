package com.example.wedstoryes.data

data class Addons(
    val type: String? = null,
    val albumsNos : Int? = null,
    val albumsPrice : Double? = null,
    val drone : Boolean? = null,
    val dronePrice : Double? = null,
    val ledScreen: Boolean?=null,
    val ledScreenCount : Int?=null,
    val ledScreenPrice: Double?=null,
    val liveStreaming: Boolean?=null,
    val liveStreamingPrice: Double?=null,
    val makeupArtist : Boolean?=null,
    val makeupArtistPrice : Double?=null,
    val decorations: Boolean?=null,
    val decorationsPrice: Double?=null,
    val invitations: Boolean?=null,
    val invitationsPrice: Double?=null
)
