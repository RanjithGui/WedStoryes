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
                        addPhotographerDetails(event.photographers,event.eventName)
                        }
                    "Video" ->{
                        addVideographerDetails(event.videographers,event.eventName)
                    }
                    "Addons" ->{
                        addAddonDetails(event.addons,event.eventName)

                    }
                    else ->{}
                }
            }
            is GlobalEvent.updateEventDetails -> {
                when(event.label){
                    "Photo" ->{
                        updatePhotographerDetails(photographers = event.photographers,eventName = event.eventName,index = event.index)
                    }
                    "Video" ->{
                        updateVideographerDetails(event.videographers,eventName = event.eventName,index = event.index)
                    }
                    "Addons" ->{
                        updateAddonAtIndex(event.index,event.addons, eventName = event.eventName)
                    }
                    else ->{}
                }
            }
            is GlobalEvent.removeEventDetails -> {}


            else -> {}
        }
    }
    fun updateSelectedEventItem(eventItem: EventItem?) {
        updateState { it.copy(selectedEventItem = eventItem) }
    }
    fun updateEvents(events: List<EventItem>) {
       updateState { it.copy(events =  events) }
    }
    fun addPhotographerDetails(photographers: Photographers,eventName: String) {
        val currentDetails = state.value.eventDetails
        val updatedDetails = currentDetails?.copy(
            photographers = currentDetails.photographers?.plus(photographers)
        ) ?: EventDetails(
            photographers = listOf(photographers),
            videographers = emptyList(),
            addons = emptyList()
        )
        val events = state.value.events.toMutableList()
        updateState { it.copy(eventDetails = updatedDetails) }
        val updatedEvents = events.map { eventItem ->
            if (eventItem.title.equals(eventName, ignoreCase = true)) {
                eventItem.copy(eventDetails = updatedDetails)
            } else {
                eventItem
            }
        }
        updateState { currentState ->
            currentState.copy(events = updatedEvents)
        }
       // updateState { it.copy(events = ) }
    }
    fun addVideographerDetails(videographers: Videographers,eventName: String) {
        val currentDetails = state.value.eventDetails
        val updatedDetails = currentDetails?.copy(
            videographers = currentDetails.videographers?.plus(videographers)
        ) ?: EventDetails(
            photographers = emptyList(),
            videographers = listOf(videographers),
            addons = emptyList()
        )
        updateState { it.copy(eventDetails = updatedDetails) }
        val currentEvents = state.value.events.toMutableList()
        val updatedEvents = currentEvents.map { eventItem ->
            if (eventItem.title.equals(eventName, ignoreCase = true)) {
                eventItem.copy(eventDetails = updatedDetails)
            } else {
                eventItem
            }
        }
        updateState { currentState ->
            currentState.copy(events = updatedEvents)
        }
    }
    fun addAddonDetails(addons: Addons,eventName: String) {
        val currentDetails = state.value.eventDetails
        val updatedDetails = currentDetails?.addons?.plus(addons)?.let {
            currentDetails.copy(
                addons = it
            )
        } ?: EventDetails(
            photographers = emptyList(),
            videographers = emptyList(),
            addons = listOf(addons)
        )
        updateState { it.copy(eventDetails = updatedDetails) }
        val currentEvents = state.value.events.toMutableList()
        val updatedEvents = currentEvents.map { eventItem ->
            if (eventItem.title.equals(eventName, ignoreCase = true)) {
                eventItem.copy(eventDetails = updatedDetails)
            } else {
                eventItem
            }
        }
        updateState { currentState ->
            currentState.copy(events = updatedEvents)
        }
    }
    fun updatePhotographerDetails(photographers: Photographers, eventName: String, index: Int) {
        val currentDetails = state.value.eventDetails ?: return
        val updatedPhotographers = currentDetails.photographers?.toMutableList() ?: mutableListOf()
        if (index >= 0 && index < updatedPhotographers.size) {
            updatedPhotographers[index] = photographers
            val updatedEventDetails = currentDetails.copy(photographers = updatedPhotographers)
            updateState { currentState ->
                currentState.copy(eventDetails = updatedEventDetails)
            }
            val currentEvents = state.value.events.toMutableList()
            val updatedEvents = currentEvents.map { eventItem ->
                if (eventItem.title.equals(eventName, ignoreCase = true)) {
                    eventItem.copy(eventDetails = updatedEventDetails)
                } else {
                    eventItem
                }
            }
            updateState { currentState ->
                currentState.copy(events = updatedEvents)
            }
            println("Successfully updated photographer at index $index")
        } else {
            println("Invalid index: $index for photographers list of size ${updatedPhotographers.size}")
        }
    }
    fun updateVideographerDetails(videographers: Videographers,eventName: String,index: Int) {
        val currentDetails = state.value.eventDetails ?: return
        val updatedVideographers = currentDetails.videographers?.toMutableList() ?: mutableListOf()
        if (index >= 0 && index < updatedVideographers.size) {
            updatedVideographers[index] = videographers
            val updatedEventDetails = currentDetails.copy(videographers = updatedVideographers)
            updateState { currentState ->
                currentState.copy(eventDetails = updatedEventDetails)
            }
            val currentEvents = state.value.events.toMutableList()
            val updatedEvents = currentEvents.map { eventItem ->
                if (eventItem.title.equals(eventName, ignoreCase = true)) {
                    eventItem.copy(eventDetails = updatedEventDetails)
                } else {
                    eventItem
                }
            }
            updateState { currentState ->
                currentState.copy(events = updatedEvents)
            }
        } else {
            println("Invalid index: $index for videographers list of size ${updatedVideographers.size}")
        }

    }

    fun updateAddonAtIndex(index: Int, newAddon: Addons,eventName: String) {
            val currentDetails = state.value.eventDetails
            currentDetails?.let { details ->
                if (index in details.addons.indices) {
                    val updatedAddons = details.addons.toMutableList()
                    updatedAddons[index] = newAddon
                    val updatedDetails = details.copy(addons = updatedAddons)
                    updateState { it.copy(eventDetails = updatedDetails) }
                    val currentEvents = state.value.events.toMutableList()
                    val updatedEvents = currentEvents.map { eventItem ->
                        if (eventItem.title.equals(eventName, ignoreCase = true)) {
                            eventItem.copy(eventDetails = updatedDetails)
                        } else {
                            eventItem
                        }
                    }
                    updateState { currentState ->
                        currentState.copy(events = updatedEvents)
                    }
                }
    }

   }

}