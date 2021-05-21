package com.aakriti.assignment.model.OpenWeatherResponseModel.InnerModel

import com.google.gson.annotations.SerializedName
import java.io.Serializable


class WeatherItem : Serializable {
    @SerializedName("icon")
    private var icon: String? = null

    @SerializedName("description")
    private var description: String? = null

    @SerializedName("main")
    private var main: String? = null

    @SerializedName("id")
    private var id = 0

    fun getIcon(): String? {
        return icon
    }

    fun setIcon(icon: String?) {
        this.icon = icon
    }

    fun getDescription(): String? {
        return description
    }

    fun setDescription(description: String?) {
        this.description = description
    }

    fun getMain(): String? {
        return main
    }

    fun setMain(main: String?) {
        this.main = main
    }

    fun getId(): Int {
        return id
    }

    fun setId(id: Int) {
        this.id = id
    }
}