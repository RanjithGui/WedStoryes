package com.example.wedstoryes

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

abstract class BaseViewModel<Event, State>(  ) : ViewModel() {
    protected abstract fun initState(): State
    private val _state = MutableStateFlow(initState())
    val state = _state.asStateFlow()
    open fun onEvent(event: Event) = Unit
    protected fun updateState(newState: (currentState: State) -> State) {
        _state.update { state -> newState(state)
        }
    }
    override fun onCleared() { super.onCleared() }
}