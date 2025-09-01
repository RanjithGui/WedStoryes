package com.example.wedstoryes.presentation.events

import com.example.wedstoryes.data.EventItem

sealed class GlobalEvent {
    data class onProceedEvent(val index: Int) : GlobalEvent()
    data class onCustomEvent(val eventItem: EventItem) : GlobalEvent()
}