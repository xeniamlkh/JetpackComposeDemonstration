package com.example.testcompose

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AppViewModel : ViewModel() {

    private var _counter = MutableStateFlow(0)
    val counter: StateFlow<Int> get() = _counter.asStateFlow()

    fun saveCounter(count: Int) {
        _counter.value = count
    }
}