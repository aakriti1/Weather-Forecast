package com.example.mvvmkotlinexample.repository

import android.util.Log

import androidx.lifecycle.MutableLiveData
import com.aakriti.assignment.model.OpenWeatherResponseModel.LocationResponseModel
import com.aakriti.assignment.utils.Constants.Companion.APP_ID
import com.example.mvvmkotlinexample.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object MainActivityRepository {

    val serviceSetterGetter = MutableLiveData<LocationResponseModel>()

    fun getWeatherApiCall(lat: String, lng: String): MutableLiveData<LocationResponseModel> {

        val call = RetrofitClient.apiInterface.getLocationData(lat,lng,APP_ID,"metric")
        call?.enqueue(object: Callback<LocationResponseModel> {
            override fun onFailure(call: Call<LocationResponseModel>, t: Throwable) {
                // TODO("Not yet implemented")
                Log.v("DEBUG : ", t.message.toString())
            }

            override fun onResponse(
                call: Call<LocationResponseModel>,
                response: Response<LocationResponseModel>
            ) {
                // TODO("Not yet implemented")
                Log.v("DEBUG : ", response.body().toString())
                val data = response.body()
                serviceSetterGetter.value = data
            }
        })

        return serviceSetterGetter
    }
}