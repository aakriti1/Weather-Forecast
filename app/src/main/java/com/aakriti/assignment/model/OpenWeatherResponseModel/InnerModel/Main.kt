package com.aakriti.assignment.model.OpenWeatherResponseModel.InnerModel

import com.google.gson.annotations.SerializedName
import java.io.Serializable


class Main : Serializable {
    @SerializedName("temp")
    private var temp = 0.0

    @SerializedName("temp_min")
    private var tempMin = 0.0

    @SerializedName("grnd_level")
    private var grndLevel = 0.0

    @SerializedName("humidity")
    private var humidity = 0

    @SerializedName("pressure")
    private var pressure = 0.0

    @SerializedName("sea_level")
    private var seaLevel = 0.0

    @SerializedName("temp_max")
    private var tempMax = 0.0

    fun getTemp(): Double {
        return temp
    }

    fun setTemp(temp: Double) {
        this.temp = temp
    }

    fun getTempMin(): Double {
        return tempMin
    }

    fun setTempMin(tempMin: Double) {
        this.tempMin = tempMin
    }

    fun getGrndLevel(): Double {
        return grndLevel
    }

    fun setGrndLevel(grndLevel: Double) {
        this.grndLevel = grndLevel
    }

    fun getHumidity(): Int {
        return humidity
    }

    fun setHumidity(humidity: Int) {
        this.humidity = humidity
    }

    fun getPressure(): Double {
        return pressure
    }

    fun setPressure(pressure: Double) {
        this.pressure = pressure
    }

    fun getSeaLevel(): Double {
        return seaLevel
    }

    fun setSeaLevel(seaLevel: Double) {
        this.seaLevel = seaLevel
    }

    fun getTempMax(): Double {
        return tempMax
    }

    fun setTempMax(tempMax: Double) {
        this.tempMax = tempMax
    }
}