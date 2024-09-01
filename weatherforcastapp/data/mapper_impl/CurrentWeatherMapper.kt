package com.example.weatherforcastapp.data.mapper_impl

import com.example.weatherforcastapp.data.mappers.ApiMapper
import com.example.weatherforcastapp.data.models.ApiWeather
import com.example.weatherforcastapp.domain.models.CurrentWeather
import com.example.weatherforcastapp.utils.Util
import com.example.weatherforcastapp.utils.WeatherInfoItem

class CurrentWeatherMapper : ApiMapper <CurrentWeather, ApiWeather.ApiCurrentWeather> {

    override fun maptoDomain(apiEntity: ApiWeather.ApiCurrentWeather): CurrentWeather {
        return CurrentWeather(
            temperature = apiEntity.temperature2m,
            time = parseTime ( apiEntity.time ),
            weatherStatus = parseWeatherStatus( apiEntity.weatherCode ),
            windDirection = parseWindDirection( apiEntity.windDirection10m ),
            windSpeed = apiEntity.windSpeed10m,
            isDay = apiEntity.isDay == 1
        )
    }

    private fun parseTime ( time : Long ) : String {
        return Util.formatUnixDate("MMM,d", time )
    }

    private fun parseWeatherStatus ( code: Int ) : WeatherInfoItem {
        return Util.getWeatherInfo( code )
    }

    private fun parseWindDirection ( windDirection: Double ) : String {
        return Util.getWindDirection( windDirection )
    }
}