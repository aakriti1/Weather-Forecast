package com.aakriti.assignment.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.aakriti.assignment.view.callback.LocationLiveData

class LocationCordinatesViewModel (application: Application) : AndroidViewModel(application) {

    private val locationData = LocationLiveData(application)

    fun getLocationData() = locationData
}
