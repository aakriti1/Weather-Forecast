package com.aakriti.assignment.model.OpenWeatherResponseModel.InnerModel

import com.google.gson.annotations.SerializedName
import java.io.Serializable


class Sys : Serializable {
    @SerializedName("country")
    private var country: String? = null

    @SerializedName("sunrise")
    private var sunrise = 0

    @SerializedName("sunset")
    private var sunset = 0

    @SerializedName("message")
    private var message = 0.0

    fun getCountry(): String? {
        return country
    }

    fun setCountry(country: String?) {
        this.country = country
    }

    fun getSunrise(): Int {
        return sunrise
    }

    fun setSunrise(sunrise: Int) {
        this.sunrise = sunrise
    }

    fun getSunset(): Int {
        return sunset
    }

    fun setSunset(sunset: Int) {
        this.sunset = sunset
    }

    fun getMessage(): Double {
        return message
    }

    fun setMessage(message: Double) {
        this.message = message
    }
}