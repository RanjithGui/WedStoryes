package com.example.wedstoryes.presentation

import com.example.wedstoryes.BaseViewModel
import com.example.wedstoryes.R
import com.example.wedstoryes.data.Addons
import com.example.wedstoryes.data.EventItem
import com.example.wedstoryes.data.Photographers
import com.example.wedstoryes.data.SubEventDetails
import com.example.wedstoryes.data.Videographers
import com.example.wedstoryes.presentation.events.GlobalEvent
import kotlin.collections.map


class GlobalViewmodel : BaseViewModel<GlobalEvent, GlobalState>() {
    override fun initState()= GlobalState()

    init {
        updateEvents(events = listOf(
            EventItem("wedding", "Wedding", R.drawable.wedding, eventDetails = emptyList()),
            EventItem("baby_shower", "Baby Shower", R.drawable.babyshower, eventDetails =  emptyList()),
            EventItem("corporate", "Corporate", R.drawable.corporate, eventDetails =  emptyList()),
            EventItem("birthday", "Birthday Party", R.drawable.birthday, eventDetails =  emptyList()),
            EventItem("customevent", "Custom Event", R.drawable.customevent, eventDetails =  emptyList())))
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
                        addPhotographerDetails(event.photographers,event.eventName,event.subEvent)
                        }
                    "Video" ->{
                        addVideographerDetails(event.videographers,event.eventName,event.subEvent)
                    }
                    "Addons" ->{
                        addAddonDetails(event.addons,event.eventName,event.subEvent)

                    }
                    else ->{}
                }
            }
            is GlobalEvent.updateEventDetails -> {
                when(event.label){
                    "Photo" ->{
                        updatePhotographerDetails(
                            photographers = event.photographers,
                            eventName = event.eventName,
                            index = event.index,event.subEvent)
                    }
                    "Video" ->{
                        updateVideographerDetails(event.videographers,
                            eventName = event.eventName,
                            index = event.index,event.subEvent)
                    }
                    "Addons" ->{
                        //updateAddonAtIndex(event.index,event.addons, eventName = event.eventName,event.subEvent)
                    }
                    else ->{}
                }
            }
            is GlobalEvent.onAddSubEvent ->{
               addSubEvent(event.subEvent,event.eventName)
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
    fun addPhotographerDetails(photographers: Photographers, eventName: String, subEvent: String) {
        val currentDetails = state.value.eventDetails

        val existingDetail = currentDetails.find { it.subEvent == subEvent }

        val updatedDetails = if (existingDetail != null) {
            // Update existing SubEventDetails
            currentDetails.map { subEventDetail ->
                if (subEventDetail.subEvent == subEvent) {
                    subEventDetail.copy(
                        photographers = subEventDetail.photographers?.plus(photographers)
                    )
                } else {
                    subEventDetail
                }
            }
        } else {
            // Add new SubEventDetails
            currentDetails + SubEventDetails(
                photographers = listOf(photographers),
                videographers = emptyList(),
                addons = emptyList(),
                subEvent = subEvent
            )
        }
        val updatedEvents = state.value.events.map { eventItem ->
            if (eventItem.title.equals(eventName, ignoreCase = true)) {
                eventItem.copy(eventDetails = updatedDetails)
            } else {
                eventItem
            }
        }
        updateState { currentState ->
            currentState.copy(
                eventDetails = updatedDetails,
                events = updatedEvents
            )
        }
    }
    fun addVideographerDetails(videographers: Videographers, eventName: String, subEvent: String) {
        val currentDetails = state.value.eventDetails
        // Find existing SubEventDetails with matching subEvent
        val existingDetail = currentDetails.find { it.subEvent == subEvent }
        val updatedDetails = if (existingDetail != null) {
            // Update existing SubEventDetails
            currentDetails.map { subEventDetail ->
                if (subEventDetail.subEvent == subEvent) {
                    subEventDetail.copy(
                        videographers = subEventDetail.videographers?.plus(videographers)
                    )
                } else {
                    subEventDetail
                }
            }
        } else {
            // Add new SubEventDetails
            currentDetails + SubEventDetails(
                photographers = emptyList(),
                videographers = listOf(videographers),
                addons = emptyList(),
                subEvent = subEvent
            )
        }
        val updatedEvents = state.value.events.map { eventItem ->
            if (eventItem.title.equals(eventName, ignoreCase = true)) {
                eventItem.copy(eventDetails = updatedDetails)
            } else {
                eventItem
            }
        }
        updateState { currentState ->
            currentState.copy(
                eventDetails = updatedDetails,
                events = updatedEvents
            )
        }
    }
    fun addAddonDetails(addons: Addons, eventName: String, subEvent: String) {
        val currentDetails = state.value.eventDetails

        // Find existing SubEventDetails with matching subEvent
        val existingDetail = currentDetails.find { it.subEvent == subEvent }

        val updatedDetails = if (existingDetail != null) {
            // Update existing SubEventDetails
            currentDetails.map { subEventDetail ->
                if (subEventDetail.subEvent == subEvent) {
                    subEventDetail.copy(
                        addons = subEventDetail.addons + addons
                    )
                } else {
                    subEventDetail
                }
            }
        } else {
            // Add new SubEventDetails
            currentDetails + SubEventDetails(
                photographers = emptyList(),
                videographers = emptyList(),
                addons = listOf(addons),
                subEvent = subEvent
            )
        }

        val updatedEvents = state.value.events.map { eventItem ->
            if (eventItem.title.equals(eventName, ignoreCase = true)) {
                eventItem.copy(eventDetails = updatedDetails)
            } else {
                eventItem
            }
        }

        updateState { currentState ->
            currentState.copy(
                eventDetails = updatedDetails,
                events = updatedEvents
            )
        }
    }
    fun addSubEvent(subEvent: String, eventName: String) {
        val currentDetails = state.value.events
        val existingDetails = currentDetails.find { it.title == eventName } ?: return // Exit if event not found
        val updatedDetails: List<SubEventDetails> = existingDetails.eventDetails.plus(
            SubEventDetails(subEvent, emptyList(), emptyList(), emptyList())
        )

        val updatedEvents = state.value.events.map { eventItem ->
            if (eventItem.title.equals(eventName, ignoreCase = true)) {
                eventItem.copy(eventDetails = updatedDetails)
            } else {
                eventItem
            }
        }
        updateState { it.copy(events = updatedEvents) }
    }
    fun updatePhotographerDetails(photographers: Photographers, eventName: String, index: Int, subEvent: String?) {
        val currentDetails = state.value.eventDetails

        val updatedEventDetails = currentDetails.map { subEventDetail ->
            if (subEventDetail.subEvent == subEvent) {
                val updatedPhotographers = subEventDetail.photographers?.toMutableList()

                if (index >= 0 && updatedPhotographers?.size?.let { index < it } == true) {
                    updatedPhotographers[index] = photographers
                    subEventDetail.copy(photographers = updatedPhotographers)
                } else {
                    println("Invalid index: $index for photographers list of size ${updatedPhotographers?.size} in subEvent '$subEvent'")
                    subEventDetail // Return unchanged if invalid index
                }
            } else {
                subEventDetail // Return unchanged if not the target subEvent
            }
        }

        // Check if any update actually happened
        if (updatedEventDetails != currentDetails) {
            val updatedEvents = state.value.events.map { eventItem ->
                if (eventItem.title.equals(eventName, ignoreCase = true)) {
                    eventItem.copy(eventDetails = updatedEventDetails)
                } else {
                    eventItem
                }
            }

            updateState { currentState ->
                currentState.copy(
                    eventDetails = updatedEventDetails,
                    events = updatedEvents
                )
            }

            println("Successfully updated photographer at index $index in subEvent '$subEvent'")
        }
    }
    fun updateVideographerDetails(videographers: Videographers, eventName: String, index: Int, subEvent: String?) {
        val currentDetails = state.value.eventDetails

        val updatedEventDetails = currentDetails.map { subEventDetail ->
            if (subEventDetail.subEvent == subEvent) {
                val updatedVideographers = subEventDetail.videographers?.toMutableList()

                if (index >= 0 && updatedVideographers?.size?.let { index < it } == true) {
                    updatedVideographers[index] = videographers
                    subEventDetail.copy(videographers = updatedVideographers)
                } else {
                    println("Invalid index: $index for videographers list of size ${updatedVideographers?.size} in subEvent '$subEvent'")
                    subEventDetail
                }
            } else {
                subEventDetail
            }
        }

        // Check if any update actually happened
        if (updatedEventDetails != currentDetails) {
            val updatedEvents = state.value.events.map { eventItem ->
                if (eventItem.title.equals(eventName, ignoreCase = true)) {
                    eventItem.copy(eventDetails = updatedEventDetails)
                } else {
                    eventItem
                }
            }

            updateState { currentState ->
                currentState.copy(
                    eventDetails = updatedEventDetails,
                    events = updatedEvents
                )
            }

            println("Successfully updated videographer at index $index in subEvent '$subEvent'")
        }
    }

   /* fun updateAddonAtIndex(index: Int, newAddon: Addons,eventName: String, subEvent: String) {
        val currentDetails = state.value.eventDetails.find { it.subEvent == subEvent }
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

   }*/

}