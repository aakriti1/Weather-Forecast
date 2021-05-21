package com.aakriti.assignment.model.OpenWeatherResponseModel.InnerModel

import com.google.gson.annotations.SerializedName
import java.io.Serializable


class Clouds: Serializable {
    @SerializedName("all")
    private var all = 0

    fun getAll(): Int {
        return all
    }

    fun setAll(all: Int) {
        this.all = all
    }
}