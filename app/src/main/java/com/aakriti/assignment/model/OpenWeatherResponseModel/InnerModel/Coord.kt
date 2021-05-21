package com.aakriti.assignment.model.OpenWeatherResponseModel.InnerModel

import com.google.gson.annotations.SerializedName
import java.io.Serializable


class Coord : Serializable {
    @SerializedName("lon")
    private var lon = 0.0

    @SerializedName("lat")
    private var lat = 0.0

    fun getLon(): Double {
        return lon
    }

    fun setLon(lon: Double) {
        this.lon = lon
    }

    fun getLat(): Double {
        return lat
    }

    fun setLat(lat: Double) {
        this.lat = lat
    }
}