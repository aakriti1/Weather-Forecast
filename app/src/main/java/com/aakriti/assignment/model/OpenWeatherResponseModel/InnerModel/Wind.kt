package com.aakriti.assignment.model.OpenWeatherResponseModel.InnerModel

import com.google.gson.annotations.SerializedName
import java.io.Serializable


class Wind : Serializable {
    @SerializedName("deg")
    private var deg = 0.0

    @SerializedName("speed")
    private var speed = 0.0

    fun getDeg(): Double {
        return deg
    }

    fun setDeg(deg: Double) {
        this.deg = deg
    }

    fun getSpeed(): Double {
        return speed
    }

    fun setSpeed(speed: Double) {
        this.speed = speed
    }
}