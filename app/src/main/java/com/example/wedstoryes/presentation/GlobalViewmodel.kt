package com.example.wedstoryes.presentation

import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import com.example.wedstoryes.BaseViewModel
import com.example.wedstoryes.R
import com.example.wedstoryes.data.Addons
import com.example.wedstoryes.data.EventDetails
import com.example.wedstoryes.data.EventItem
import com.example.wedstoryes.data.Photographers
import com.example.wedstoryes.data.Videographers
import com.example.wedstoryes.presentation.events.GlobalEvent



class GlobalViewmodel : BaseViewModel<GlobalEvent, GlobalState>() {
    override fun initState()= GlobalState()

    init {
        updateEvents(events = listOf(
            EventItem("wedding", "Wedding", R.drawable.wedding, eventDetails = EventDetails()),
            EventItem("baby_shower", "Baby Shower", R.drawable.babyshower,eventDetails = EventDetails()),
            EventItem("corporate", "Corporate", R.drawable.corporate,eventDetails = EventDetails()),
            EventItem("birthday", "Birthday Party", R.drawable.birthday,eventDetails = EventDetails()),
            EventItem("customevent", "Custom Event", R.drawable.customevent,eventDetails = EventDetails()),
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
            is GlobalEvent.onAddEventDetails -> {

                when(event.label){
                    "Photo" ->{
                        updatePhotographerDetails(photographers = Photographers(1,"Candid","10000","Expert in wedding photography with 10 years of experience."),eventName = event.eventName)}
                    "Video" ->{}
                    "Other" ->{}
                    else ->{}
                }
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
    fun updatePhotographerDetails(photographers: Photographers,eventName: String) {
        println("Before update - eventDetails: ${state.value.eventDetails}")

        val currentDetails = state.value.eventDetails
        println("Current details: $currentDetails")

        val updatedDetails = currentDetails?.copy(photographers = photographers)
            ?: EventDetails(
                photographers = photographers,
                videographers = Videographers(),
                addons = emptyList()
            )

        println("Updated details before state update: $updatedDetails")

        updateState { currentState ->
            val newState = currentState.copy(eventDetails = updatedDetails)
            println("New state after update: $newState")
            newState
        }

        println("Final state check: ${state.value.eventDetails}")
    }
    fun updateVideographerDetails(videographers: Videographers) {
        val currentDetails = state.value.eventDetails
        val updatedDetails = currentDetails?.copy(videographers = videographers)
        updateState { it.copy(eventDetails = updatedDetails) }
    }

    fun updateAddonAtIndex(index: Int, newAddon: Addons) {
            val currentDetails = state.value.eventDetails
            currentDetails?.let { details ->
                if (index in details.addons.indices) {
                    val updatedAddons = details.addons.toMutableList()
                    updatedAddons[index] = newAddon
                    val updatedDetails = details.copy(addons = updatedAddons)
                    updateState { it.copy(eventDetails = updatedDetails) }
                }
    }

}
    }