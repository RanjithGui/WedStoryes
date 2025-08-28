package com.example.wedstoryes.presentation

import com.example.wedstoryes.BaseViewModel
import com.example.wedstoryes.presentation.events.GlobalEvent



class Viewmodel : BaseViewModel<GlobalEvent, GlobalState>() {
    override fun initState()= GlobalState()

    override fun onEvent(event: GlobalEvent) {
        when (event) {

            else -> {}
        }
    }
}