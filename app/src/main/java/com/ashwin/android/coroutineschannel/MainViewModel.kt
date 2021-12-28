package com.ashwin.android.coroutineschannel

import android.util.Log
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
        val event = "Hello Channel $channelCount"
        channelCount++
        Log.d("coroutines-channel", "triggerEvent sending...: $event")
        eventChannel.send(event)  // Until events are received, this new coroutine will not proceed.

        // Reachable only after all the sent events are received/observed.
        // Do not do anything here, since it is possible that no one is observing and this is unreachable.
        Log.d("coroutines-channel", "triggerEvent sent: $event")
        //channelCount++
    }

    fun triggerData() = viewModelScope.launch {
        val data = "Hello LiveData: $liveDataCount"
        Log.d("coroutines-channel", "triggerData sending...: $data")
        //_liveData.postValue(data)
        _liveData.value = data

        // Reachable even if no oberver is observing
        Log.d("coroutines-channel", "triggerData sent: $data")
        liveDataCount++
    }
}
