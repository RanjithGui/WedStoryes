package com.example.wedstoryes.presentation

import com.example.wedstoryes.BaseViewModel
import com.example.wedstoryes.R
import com.example.wedstoryes.data.EventItem
import com.example.wedstoryes.presentation.events.GlobalEvent



class GlobalViewmodel : BaseViewModel<GlobalEvent, GlobalState>() {
    override fun initState()= GlobalState()

    init {
        updateEvents(events = listOf(
            EventItem("wedding", "Wedding", R.drawable.wedding),
            EventItem("baby_shower", "Baby Shower", R.drawable.babyshower),
            EventItem("corporate", "Corporate", R.drawable.corporate),
            EventItem("birthday", "Birthday Party", R.drawable.birthday),
            EventItem("customevent", "Custom Event", R.drawable.customevent),
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
                updateSelectedEventItem(state.value.events[state.value.selectedEventItemIndex])
            }
            is GlobalEvent.onDeleteEvent -> {
                val events = state.value.events.toMutableList()
                events.remove(event.eventItem)
                updateEvents(events)
                updateState { it.copy(selectedEventItemIndex = -1) }
            }


            else -> {}
        }
    }
    fun updateSelectedEventItem(eventItem: EventItem?) {
        updateState { it.copy(selectedEventItem = eventItem) }
    }
    fun updateEvents(events: List<EventItem>) {
       updateState { it.copy(events =  events) }
    }
}