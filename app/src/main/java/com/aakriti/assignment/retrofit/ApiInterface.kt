package com.example.mvvmkotlinexample.retrofit

import com.aakriti.assignment.model.OpenWeatherResponseModel.LocationResponseModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("data/2.5/weather")
    fun getLocationData(
        @Query("lat") lat: String?,
        @Query("lon") lon: String?,
        @Query("APPID") app_id: String?,
        @Query("units") imperial: String?

    ): Call<LocationResponseModel>


}