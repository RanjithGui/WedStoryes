package com.example.wedstoryes.presentation

import com.example.wedstoryes.data.EventItem
import com.example.wedstoryes.data.SubEventDetails

data class GlobalState(
    val isLoding: Boolean = false,
    val events : List<EventItem> = emptyList(),
    val selectedEventItemIndex: Int = -1,
    val selectedEventItem: EventItem? = null,
    val eventDetails: List<SubEventDetails> = emptyList()

)