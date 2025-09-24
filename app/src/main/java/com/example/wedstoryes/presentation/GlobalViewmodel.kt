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
                        updateAddonAtIndex(event.index,event.addons,event.eventName,event.subEvent)
                    }
                    else ->{}
                }
            }
            is GlobalEvent.onAddSubEvent ->{
               addSubEvent(event.subEvent,event.eventName,event.time,event.date)
            }
            is GlobalEvent.removeEventDetails -> {
                when(event.label){
                    "Photo" ->{
                        deletePhotographerDetails(
                            eventName = event.eventName,
                            index = event.index, subEvent = event.subEvent)
                    }
                    "Video" ->{
                        deleteVideoGrapherDetails(
                            eventName = event.eventName,
                            index = event.index,subEvent=event.subEvent)
                    }
                    "Addons" ->{
                        deleteAddonDetails(index = event.index, eventName = event.eventName, subEvent = event.subEvent)
                    }


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
    fun deletePhotographerDetails(
        eventName: String,
        subEvent: String?,
        index: Int
    ) {
        val event = state.value.events.find { it.title.equals(eventName, ignoreCase = true) }
        val existingSubEventDetail = event?.eventDetails?.find { it.subEvent == subEvent }
        if (existingSubEventDetail!=null){
            val updatedEventDetails = event.eventDetails.map { subEventDetail ->
                if (subEventDetail.subEvent == subEvent) {
                    val currentPhotographers = subEventDetail.photographers ?: emptyList()
                    val updatedPhotographers = if (index >= 0 && index < currentPhotographers.size) {
                        currentPhotographers.toMutableList().apply { removeAt(index) }
                    } else {
                        currentPhotographers
                    }
                    subEventDetail.copy(photographers = updatedPhotographers)
                } else {
                    subEventDetail
                }
            }
            val finalEventDetails = updatedEventDetails.filter { subEventDetail ->
                if (subEventDetail.subEvent == subEvent) {
                    val photographersEmpty = subEventDetail.photographers.isNullOrEmpty()
                    val videographersEmpty = subEventDetail.videographers.isNullOrEmpty()
                    val addonsEmpty = subEventDetail.addons.isNullOrEmpty()
                    !(photographersEmpty && videographersEmpty && addonsEmpty)
                } else {
                    true // Keep other subEvents
                }
            }
            val updatedEvents = state.value.events.map { eventItem ->
                if (eventItem.title.equals(eventName, ignoreCase = true)) {
                    eventItem.copy(eventDetails = finalEventDetails)
                } else {
                    eventItem
                }
            }
            updateState { currentState ->
                currentState.copy(events = updatedEvents)
            }
        }
    }
    fun deleteVideoGrapherDetails(
        eventName: String,
        subEvent: String?,
        index: Int
    ) {
        val event = state.value.events.find { it.title.equals(eventName, ignoreCase = true) }
        val existingSubEventDetail = event?.eventDetails?.find { it.subEvent == subEvent }
        if (existingSubEventDetail!=null){
            val updatedEventDetails = event.eventDetails.map { subEventDetail ->
                if (subEventDetail.subEvent == subEvent) {
                    val currentVideoGraphers = subEventDetail.videographers ?: emptyList()

                    val updatedVideoGraphers = if (index >= 0 && index < currentVideoGraphers.size) {
                        currentVideoGraphers.toMutableList().apply { removeAt(index) }
                    } else {
                        currentVideoGraphers
                    }
                    subEventDetail.copy(videographers = updatedVideoGraphers)
                } else {
                    subEventDetail
                }
            }
            val finalEventDetails = updatedEventDetails.filter { subEventDetail ->
                if (subEventDetail.subEvent == subEvent) {
                    val photographersEmpty = subEventDetail.photographers.isNullOrEmpty()
                    val videographersEmpty = subEventDetail.videographers.isNullOrEmpty()
                    val addonsEmpty = subEventDetail.addons.isNullOrEmpty()
                    !(photographersEmpty && videographersEmpty && addonsEmpty)
                } else {
                    true // Keep other subEvents
                }
            }
            val updatedEvents = state.value.events.map { eventItem ->
                if (eventItem.title.equals(eventName, ignoreCase = true)) {
                    eventItem.copy(eventDetails = finalEventDetails)
                } else {
                    eventItem
                }
            }
            updateState { currentState ->
                currentState.copy(events = updatedEvents)
            }
        }
    }
    fun deleteAddonDetails(
        eventName: String,
        subEvent: String?,
        index: Int
    ) {
        val event = state.value.events.find { it.title.equals(eventName, ignoreCase = true) }
        val existingSubEventDetail = event?.eventDetails?.find { it.subEvent == subEvent }
        if (existingSubEventDetail!=null){
            val updatedEventDetails = event.eventDetails.map { subEventDetail ->
                if (subEventDetail.subEvent == subEvent) {
                    val currentAddons = subEventDetail.addons ?: emptyList()

                    val updatedAddons = if (index >= 0 && index < currentAddons.size) {
                        currentAddons.toMutableList().apply { removeAt(index) }
                    } else {
                        currentAddons
                    }
                    subEventDetail.copy(addons = updatedAddons)
                } else {
                    subEventDetail
                }
            }
            val finalEventDetails = updatedEventDetails.filter { subEventDetail ->
                if (subEventDetail.subEvent == subEvent) {
                    val photographersEmpty = subEventDetail.photographers.isNullOrEmpty()
                    val videographersEmpty = subEventDetail.videographers.isNullOrEmpty()
                    val addonsEmpty = subEventDetail.addons.isNullOrEmpty()
                    !(photographersEmpty && videographersEmpty && addonsEmpty)
                } else {
                    true // Keep other subEvents
                }
            }
            val updatedEvents = state.value.events.map { eventItem ->
                if (eventItem.title.equals(eventName, ignoreCase = true)) {
                    eventItem.copy(eventDetails = finalEventDetails)
                } else {
                    eventItem
                }
            }
            updateState { currentState ->
                currentState.copy(events = updatedEvents)
            }
        }
    }

    fun addPhotographerDetails(photographers: Photographers, eventName: String, subEvent: String) {
        val event = state.value.events.find { it.title.equals(eventName, ignoreCase = true) }
        val existingSubEventDetail = event?.eventDetails?.find { it.subEvent == subEvent }
        if (existingSubEventDetail != null) {
            val updatedEventDetails = event.eventDetails.map { subEventDetail ->
                if (subEventDetail.subEvent == subEvent) {
                    subEventDetail.copy(
                        photographers = subEventDetail.photographers?.plus(photographers)
                    )
                } else {
                    subEventDetail
                }
            }

            val updatedEvents = state.value.events.map { eventItem ->
                if (eventItem.title.equals(eventName, ignoreCase = true)) {
                    eventItem.copy(eventDetails = updatedEventDetails)
                } else {
                    eventItem
                }
            }
            updateState { currentState ->
                currentState.copy(
                    events = updatedEvents
                )
            }
        } else {
            println("SubEvent '$subEvent' does not exist for event '$eventName'")        }
    }
    fun addVideographerDetails(videographers: Videographers, eventName: String, subEvent: String) {
        val event = state.value.events.find { it.title.equals(eventName, ignoreCase = true) }
        val existingSubEventDetail = event?.eventDetails?.find { it.subEvent == subEvent }
        if (existingSubEventDetail != null) {
            // SubEvent exists, proceed to add/update photographer details
            val updatedEventDetails = event.eventDetails.map { subEventDetail ->
                if (subEventDetail.subEvent == subEvent) {
                    subEventDetail.copy(
                        videographers = subEventDetail.videographers?.plus(videographers)
                    )
                } else {
                    subEventDetail
                }
            }
            val updatedEvents = state.value.events.map { eventItem ->
                if (eventItem.title.equals(eventName, ignoreCase = true)) {
                    eventItem.copy(eventDetails = updatedEventDetails)
                } else {
                    eventItem
                }
            }
            updateState { currentState ->
                currentState.copy(
                    events = updatedEvents
                )
            }
        } else {
            println("SubEvent '$subEvent' does not exist for event '$eventName'")        }
    }
    fun addAddonDetails(addons: Addons, eventName: String, subEvent: String) {
        val event = state.value.events.find { it.title.equals(eventName, ignoreCase = true) }
        val existingSubEventDetail = event?.eventDetails?.find { it.subEvent == subEvent }
        if (existingSubEventDetail != null) {
            val updatedEventDetails = event.eventDetails.map { subEventDetail ->
                if (subEventDetail.subEvent == subEvent) {
                        subEventDetail.copy(
                            addons = subEventDetail.addons?.plus(addons) ?: listOf(addons)
                        )
                } else {
                    subEventDetail
                }
            }
            val updatedEvents = state.value.events.map { eventItem ->
                if (eventItem.title.equals(eventName, ignoreCase = true)) {
                    eventItem.copy(eventDetails = updatedEventDetails)
                } else {
                    eventItem
                }
            }
            updateState { currentState ->
                currentState.copy(
                    events = updatedEvents
                )
            }
        } else {
            println("SubEvent '$subEvent' does not exist for event '$eventName'")        }
    }
    fun addSubEvent(subEvent: String, eventName: String,time: String,date: String) {
        val currentDetails = state.value.events
        val existingDetails = currentDetails.find { it.title == eventName } ?: return // Exit if event not found
        val updatedDetails: List<SubEventDetails> = existingDetails.eventDetails.plus(
            SubEventDetails(subEvent, time, date, emptyList(),emptyList(),emptyList())
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
        // Find the specific EventItem
        val eventToUpdate = state.value.events.find { it.title.equals(eventName, ignoreCase = true) }
        if (eventToUpdate == null) {
            println("Event '$eventName' not found for updating photographer.")
            return
        }

        // Find the specific SubEventDetails within that EventItem
        val subEventDetailToUpdate = eventToUpdate.eventDetails.find { it.subEvent == subEvent }
        if (subEventDetailToUpdate == null) {
            println("SubEvent '$subEvent' not found in event '$eventName' for updating photographer.")
            return
        }

        // Get the current list of photographers for that sub-event
        val currentPhotographersList = subEventDetailToUpdate.photographers

        if (currentPhotographersList != null && index >= 0 && index < currentPhotographersList.size) {
            // Create a mutable copy of the photographers list
            val mutablePhotographers = currentPhotographersList.toMutableList()
            // Replace the item at the specified index
            mutablePhotographers[index] = photographers

            // Create updated SubEventDetails with the modified photographers list
            val updatedSubEventDetails = subEventDetailToUpdate.copy(photographers = mutablePhotographers)

            // Create updated eventDetails list for the specific event
            val updatedEventDetailsListForEvent = eventToUpdate.eventDetails.map { detail ->
                if (detail.subEvent == subEvent) {
                    updatedSubEventDetails
                } else {
                    detail
                }
            }

            // Create the updated EventItem
            val updatedEventItem = eventToUpdate.copy(eventDetails = updatedEventDetailsListForEvent)

            // Update the main events list in the state
            val updatedEventsList = state.value.events.map { eventItem ->
                if (eventItem.title.equals(eventName, ignoreCase = true)) {
                    updatedEventItem
                } else {
                    eventItem
                }
            }

            updateState { currentState ->
                currentState.copy(
                    events = updatedEventsList
                    // If state.value.eventDetails is a flat list of all sub-event details,
                    // you'll need to rebuild it here based on the updatedEventsList.
                    // For example:
                    // eventDetails = updatedEventsList.flatMap { it.eventDetails }
                )
            }
            println("Successfully updated photographer at index $index in subEvent '$subEvent' of event '$eventName'")

        } else {
            println("Invalid index: $index for photographers list of size ${currentPhotographersList?.size} in subEvent '$subEvent', or photographers list is null.")
        }
    }

    fun updateVideographerDetails(videographers: Videographers, eventName: String, index: Int, subEvent: String?) {
        val eventToUpdate = state.value.events.find { it.title.equals(eventName, ignoreCase = true) }
        if (eventToUpdate == null) {
            println("Event '$eventName' not found for updating photographer.")
            return
        }

        val subEventDetailToUpdate = eventToUpdate.eventDetails.find { it.subEvent == subEvent }
        if (subEventDetailToUpdate == null) {
            println("SubEvent '$subEvent' not found in event '$eventName' for updating photographer.")
            return
        }
        val currentVideographersList = subEventDetailToUpdate.videographers

        if (currentVideographersList != null && index >= 0 && index < currentVideographersList.size) {

            val mutableVideographers = currentVideographersList.toMutableList()

            mutableVideographers[index] = videographers

            val updatedSubEventDetails = subEventDetailToUpdate.copy(videographers = mutableVideographers)

            val updatedEventDetailsListForEvent = eventToUpdate.eventDetails.map { detail ->
                if (detail.subEvent == subEvent) {
                    updatedSubEventDetails
                } else {
                    detail
                }
            }
            // Create the updated EventItem
            val updatedEventItem = eventToUpdate.copy(eventDetails = updatedEventDetailsListForEvent)

            // Update the main events list in the state
            val updatedEventsList = state.value.events.map { eventItem ->
                if (eventItem.title.equals(eventName, ignoreCase = true)) {
                    updatedEventItem
                } else {
                    eventItem
                }
            }

            updateState { currentState ->
                currentState.copy(
                    events = updatedEventsList

                )
            }
            println("Successfully updated photographer at index $index in subEvent '$subEvent' of event '$eventName'")

        } else {
            println("Invalid index: $index for photographers list of size ${currentVideographersList?.size} in subEvent '$subEvent', or photographers list is null.")
        }
    }

    fun updateAddonAtIndex(index: Int, newAddon: Addons, eventName: String, subEvent: String?) {
        val eventToUpdate = state.value.events.find { it.title.equals(eventName, ignoreCase = true) }
        if (eventToUpdate == null) {
            println("Event '$eventName' not found for updating photographer.")
            return
        }

        // Find the specific SubEventDetails within that EventItem
        val subEventDetailToUpdate = eventToUpdate.eventDetails.find { it.subEvent == subEvent }
        if (subEventDetailToUpdate == null) {
            println("SubEvent '$subEvent' not found in event '$eventName' for updating photographer.")
            return
        }

        // Get the current list of photographers for that sub-event
        val currentAddonsList = subEventDetailToUpdate.addons

        if (currentAddonsList != null && index >= 0 && index < currentAddonsList.size) {
            // Create a mutable copy of the photographers list
            val mutableAddons = currentAddonsList.toMutableList()
            // Replace the item at the specified index
            mutableAddons[index] = newAddon

            // Create updated SubEventDetails with the modified photographers list
            val updatedSubEventDetails = subEventDetailToUpdate.copy(addons = mutableAddons)

            // Create updated eventDetails list for the specific event
            val updatedEventDetailsListForEvent = eventToUpdate.eventDetails.map { detail ->
                if (detail.subEvent == subEvent) {
                    updatedSubEventDetails
                } else {
                    detail
                }
            }

            // Create the updated EventItem
            val updatedEventItem = eventToUpdate.copy(eventDetails = updatedEventDetailsListForEvent)

            // Update the main events list in the state
            val updatedEventsList = state.value.events.map { eventItem ->
                if (eventItem.title.equals(eventName, ignoreCase = true)) {
                    updatedEventItem
                } else {
                    eventItem
                }
            }

            updateState { currentState ->
                currentState.copy(
                    events = updatedEventsList
                    // If state.value.eventDetails is a flat list of all sub-event details,
                    // you'll need to rebuild it here based on the updatedEventsList.
                    // For example:
                    // eventDetails = updatedEventsList.flatMap { it.eventDetails }
                )
            }
            println("Successfully updated photographer at index $index in subEvent '$subEvent' of event '$eventName'")

        } else {
            println("Invalid index: $index for photographers list of size ${currentAddonsList?.size} in subEvent '$subEvent', or photographers list is null.")
        }

   }

}