package com.example.wedstoryes.presentation

import com.example.wedstoryes.data.EventItem

data class GlobalState(
    val isLoding: Boolean = false,
    val events : List<EventItem> = emptyList(),
    val selectedEventItemIndex: Int = -1
)