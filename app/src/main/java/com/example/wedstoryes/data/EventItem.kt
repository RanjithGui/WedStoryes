package com.example.wedstoryes.data

data class EventItem(
    val id: String?=null,
    val title: String?=null,
    val videoUri: Int?=null,
    val eventDetails: List<SubEventDetails> = emptyList(),
    val ownerDetails: OwnerDetails?=null,
    val clientDetails: ClientDetails?=null,
    val termsAndConditions: String? = null,
    val termsAndConditionsSaved: Boolean?=false
)