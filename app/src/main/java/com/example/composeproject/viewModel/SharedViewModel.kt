package com.example.composeproject.viewModel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

class SharedViewModel : ViewModel() {

//    private var _currentMessage = MutableStateFlow("")
//    val currentMessage = _currentMessage.asStateFlow()
    var currentMessage = MutableStateFlow("")

    private var _message = MutableStateFlow(currentMessage.value)
    val message = _message.asStateFlow()

    fun setMessage(text: String) {
        currentMessage.value = text
//        _message.value = text
    }

    fun sendMessage() {
        _message.value = currentMessage.value
    }
}
