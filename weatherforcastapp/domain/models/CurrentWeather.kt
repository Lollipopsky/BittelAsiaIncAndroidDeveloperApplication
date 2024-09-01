package com.example.weatherforcastapp.domain.models

import com.example.weatherforcastapp.utils.WeatherInfoItem

data class CurrentWeather (
    val temperature: Double,
    val time: String,
    val weatherStatus: WeatherInfoItem,
    val windDirection: String,
    val windSpeed: Double,
    val isDay: Boolean
)
