package com.example.weatherforcastapp.domain.repository

import com.example.weatherforcastapp.domain.models.Weather
import com.example.weatherforcastapp.utils.Response
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {

    fun getWeatherData(toFloat: Float, toFloat1: Float): Flow <Response<Weather>>
}