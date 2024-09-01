package com.example.weatherforcastapp.domain.models

data class Weather (
    val currentWeather: CurrentWeather,
    val daily: Daily,
    val hourly: Hourly
)