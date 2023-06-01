package com.example.composeproject.viewModel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SharedViewModel : ViewModel() {

    private var _currentMessage = MutableStateFlow("")
    val currentMessage = _currentMessage.asStateFlow()
//    private var currentMessage = ""

    private var _message = MutableStateFlow("")
    val message = _message.asStateFlow()

    fun setFirstFragmentMessage(text: String) {
        _currentMessage.value = text
    }
    fun setSecondFragmentMessage(text: String) {
        _message.value = text
    }

    fun sendMessageToSecondFragment() {
        _message.value = currentMessage.value
    }
    fun sendMessageToFirstFragment() {
        _currentMessage.value = message.value
    }
}
