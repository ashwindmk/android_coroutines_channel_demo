package com.ashwin.android.coroutineschannel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val eventChannel = Channel<String>()
    val eventFlow = eventChannel.receiveAsFlow()

    private val _liveData = MutableLiveData<String>("Data 0")
    val liveData = _liveData

    private var channelCount = 1
    private var liveDataCount = 1

    fun triggerEvent() = viewModelScope.launch {
        eventChannel.send("Hello Channel $channelCount")
        channelCount++
    }

    fun triggerData() = viewModelScope.launch {
        _liveData.postValue("Hello LiveData: $liveDataCount")
        liveDataCount++
    }
}
