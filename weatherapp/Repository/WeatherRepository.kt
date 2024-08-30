package com.example.weatherapp.Repository

import com.example.weatherapp.Server.ApiServices

class WeatherRepository(val api:ApiServices) {

    fun getCurrentWeather(lat: Double,lng: Double,unit: String)=
        api.getCurrentWeather(lat,lng,unit, "5c0a87fe145c11468bddbedd8228df0f")

    fun getForecastWeather(lat: Double,lng: Double,unit: String)=
        api.getForecastWeather(lat,lng,unit, "5c0a87fe145c11468bddbedd8228df0f")
}