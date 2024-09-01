package com.example.weatherforcastapp.data.repository

import com.example.weatherforcastapp.data.mapper_impl.ApiWeatherMapper
import com.example.weatherforcastapp.data.remote.WeatherApi
import com.example.weatherforcastapp.domain.models.Weather
import com.example.weatherforcastapp.domain.repository.WeatherRepository
import com.example.weatherforcastapp.utils.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherApi : WeatherApi,
    private val apiWeatherMapper: ApiWeatherMapper

) :WeatherRepository {
    override fun getWeatherData(toFloat: Float, toFloat1: Float): Flow<Response<Weather>> = flow {
        emit ( Response.Loading () )
        val apiWeather = weatherApi.getWeatherData()
        val weather = apiWeatherMapper.maptoDomain(apiWeather)
        emit ( Response.Success ( weather ))
    } .catch { e->
        e.printStackTrace ()
        emit( Response.Error ( e.message.orEmpty () ) )
    }

    }

