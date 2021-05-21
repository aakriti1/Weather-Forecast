package com.aakriti.assignment.model.OpenWeatherResponseModel

import com.aakriti.assignment.model.OpenWeatherResponseModel.InnerModel.*
import com.google.gson.annotations.SerializedName
import java.io.Serializable


class LocationResponseModel : Serializable{
    @SerializedName("dt")
    private var dt = 0

    @SerializedName("coord")
    private var coord: Coord? = null

    @SerializedName("weather")
    private var weather: List<WeatherItem?>? = null

    @SerializedName("name")
    private var name: String? = null

    @SerializedName("cod")
    private var cod = 0

    @SerializedName("main")
    private var main: Main? = null

    @SerializedName("clouds")
    private var clouds: Clouds? = null

    @SerializedName("id")
    private var id = 0

    @SerializedName("sys")
    private var sys: Sys? = null

    @SerializedName("base")
    private var base: String? = null

    @SerializedName("wind")
    private var wind: Wind? = null

    fun getDt(): Int {
        return dt
    }

    fun setDt(dt: Int) {
        this.dt = dt
    }

    fun getCoord(): Coord? {
        return coord
    }

    fun setCoord(coord: Coord?) {
        this.coord = coord
    }

    fun getWeather(): List<WeatherItem?>? {
        return weather
    }

    fun setWeather(weather: List<WeatherItem?>?) {
        this.weather = weather
    }

    fun getName(): String? {
        return name
    }

    fun setName(name: String?) {
        this.name = name
    }

    fun getCod(): Int {
        return cod
    }

    fun setCod(cod: Int) {
        this.cod = cod
    }

    fun getMain(): Main? {
        return main
    }

    fun setMain(main: Main?) {
        this.main = main
    }

    fun getClouds(): Clouds? {
        return clouds
    }

    fun setClouds(clouds: Clouds?) {
        this.clouds = clouds
    }

    fun getId(): Int {
        return id
    }

    fun setId(id: Int) {
        this.id = id
    }

    fun getSys(): Sys? {
        return sys
    }

    fun setSys(sys: Sys?) {
        this.sys = sys
    }

    fun getBase(): String? {
        return base
    }

    fun setBase(base: String?) {
        this.base = base
    }

    fun getWind(): Wind? {
        return wind
    }

    fun setWind(wind: Wind?) {
        this.wind = wind
    }
}

