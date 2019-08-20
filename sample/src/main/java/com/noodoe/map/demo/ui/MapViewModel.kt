package com.noodoe.map.demo.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.GoogleMap
import com.noodoe.map.demo.model.Event

class MapViewModel : ViewModel() {

    private val _mapCenterEvent = MutableLiveData<Event<Boolean>>()
    val mapCenterEvent: LiveData<Event<Boolean>>
        get() = _mapCenterEvent

    var googleMap: GoogleMap? = null

    fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap

        _mapCenterEvent.postValue(Event(true))
    }
}