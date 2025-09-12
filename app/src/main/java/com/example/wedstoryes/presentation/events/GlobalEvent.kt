package com.example.wedstoryes.presentation.events

import com.example.wedstoryes.data.EventDetails
import com.example.wedstoryes.data.EventItem

sealed class GlobalEvent {
    data class onProceedEvent(val index: Int) : GlobalEvent()
    data class onCustomEvent(val eventItem: EventItem) : GlobalEvent()
    data class onDeleteEvent(val eventItem: EventItem) : GlobalEvent()
    data class onAddEventDetails(val label: String, val eventName: String) : GlobalEvent()
    data class updateEventDetails(val eventDetails: EventDetails) : GlobalEvent()

}