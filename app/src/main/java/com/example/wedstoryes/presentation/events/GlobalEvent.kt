package com.example.wedstoryes.presentation.events

import com.example.wedstoryes.data.Addons
import com.example.wedstoryes.data.ClientDetails
import com.example.wedstoryes.data.EventItem
import com.example.wedstoryes.data.OwnerDetails
import com.example.wedstoryes.data.Photographers
import com.example.wedstoryes.data.Videographers

sealed class GlobalEvent {
    data class onProceedEvent(val index: Int) : GlobalEvent()
    data class onCustomEvent(val eventItem: EventItem) : GlobalEvent()
    data class onDeleteEvent(val eventItem: EventItem) : GlobalEvent()
    data class onAddEventDetails(val label: String, val eventName: String, val photographers: Photographers,val videographers: Videographers,val addons: Addons,val subEvent: String) : GlobalEvent()
    data class updateEventDetails(
        val eventName: String, val index: Int, val label: String, val photographers: Photographers, val videographers: Videographers, val addons: Addons,
        val subEvent: String?
    ) : GlobalEvent()
    data class onAddSubEvent(
        val eventName: String, val photographers: Photographers,
        val videographers: Videographers,
        val addons: Addons,
        val subEvent: String?,
        val time: String,
        val date: String
    ): GlobalEvent()
    data class removeEventDetails(
        val eventName: String,
        val index: Int,
        val label: String,
        val photographers: Photographers,
        val videographers: Videographers,
        val addons: Addons,
        val subEvent: String?
    ) : GlobalEvent()
    data class onSaveEvent(
        val type: String,
        val selectedEvent: String?,
        val ownerDetails: OwnerDetails,
        val clientDetails: ClientDetails,
        val termsAndConditions: String
    ): GlobalEvent()

}