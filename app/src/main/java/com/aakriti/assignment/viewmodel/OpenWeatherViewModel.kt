package com.aakriti.assignment.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aakriti.assignment.model.OpenWeatherResponseModel.LocationResponseModel
import com.example.mvvmkotlinexample.repository.MainActivityRepository

class OpenWeatherViewModel : ViewModel() {

    var weatherLiveData: LiveData<LocationResponseModel>? = null

    fun getWeatherData(lat: String, lng: String) : LiveData<LocationResponseModel>? {
        weatherLiveData = MainActivityRepository.getWeatherApiCall(lat,lng)
        return weatherLiveData
    }

}