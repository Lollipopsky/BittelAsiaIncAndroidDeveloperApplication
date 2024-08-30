package com.example.weatherapp.model

import com.google.gson.annotations.SerializedName

data class ForecastResponseApi(
    @SerializedName("cnt")
    val cnt: Int?,
    @SerializedName("cod")
    val cod: String?,
    @SerializedName("list")
    val list: List<data>?,
    @SerializedName("message")
    val message: Int?
) {
    data class data(
        @SerializedName("clouds")
        val clouds: Clouds?,
        @SerializedName("dt")
        val dt: Int?,
        @SerializedName("dt_txt")
        val dtTxt: String?,
        @SerializedName("main")
        val main: Main?,
        @SerializedName("pop")
        val pop: Double?,
        @SerializedName("rain")
        val rain: Rain?,
        @SerializedName("sys")
        val sys: Sys?,
        @SerializedName("visibility")
        val visibility: Int?,
        @SerializedName("weather")
        val weather: List<Weather?>?,
        @SerializedName("wind")
        val wind: Wind?
    ) {
        data class Clouds(
            @SerializedName("all")
            val all: Int?
        )

        data class Main(
            @SerializedName("feels_like")
            val feelsLike: Double?,
            @SerializedName("grnd_level")
            val grndLevel: Int?,
            @SerializedName("humidity")
            val humidity: Int?,
            @SerializedName("pressure")
            val pressure: Int?,
            @SerializedName("sea_level")
            val seaLevel: Int?,
            @SerializedName("temp")
            val temp: Double?,
            @SerializedName("temp_kf")
            val tempKf: Double?,
            @SerializedName("temp_max")
            val tempMax: Double?,
            @SerializedName("temp_min")
            val tempMin: Double?
        )

        data class Rain(
            @SerializedName("3h")
            val threeHourVolume: Double? // Renamed from 'h' to 'threeHourVolume'
        )

        data class Sys(
            @SerializedName("pod")
            val pod: String?
        )

        data class Weather(
            @SerializedName("description")
            val description: String?,
            @SerializedName("icon")
            val icon: String?,
            @SerializedName("id")
            val id: Int?,
            @SerializedName("main")
            val main: String?
        )

        data class Wind(
            @SerializedName("deg")
            val deg: Int?,
            @SerializedName("gust")
            val gust: Double?,
            @SerializedName("speed")
            val speed: Double?
        )
    }
}
