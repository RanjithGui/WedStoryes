package com.example.wedstoryes.presentation

import com.example.wedstoryes.BaseViewModel
import com.example.wedstoryes.R
import com.example.wedstoryes.data.EventItem
import com.example.wedstoryes.presentation.events.GlobalEvent



class GlobalViewmodel : BaseViewModel<GlobalEvent, GlobalState>() {
    override fun initState()= GlobalState()

    init {
        updateEvents(events = listOf(
            EventItem("wedding", "Wedding", R.raw.wedding),
            EventItem("baby_shower", "Baby Shower", R.raw.babyshower),
            EventItem("corporate", "Corporate", R.raw.corporate),
            EventItem("birthday", "Birthday Party", R.raw.birthday),
            EventItem("customevent", "Custom Event", R.raw.customevent),
        ))
    }

    override fun onEvent(event: GlobalEvent) {
        when (event) {
            is GlobalEvent.onProceedEvent -> {
                updateState { it.copy(selectedEventItemIndex = event.index) }
            }
            is GlobalEvent.onCustomEvent -> {
              val events = state.value.events.toMutableList()
                events.add(events.size -1 ,event.eventItem)
              updateEvents(events)
                updateState { it.copy(selectedEventItemIndex = events.size-2) }
            }

            else -> {}
        }
    }
    fun updateEvents(events: List<EventItem>) {
       updateState { it.copy(events =  events) }
    }
}