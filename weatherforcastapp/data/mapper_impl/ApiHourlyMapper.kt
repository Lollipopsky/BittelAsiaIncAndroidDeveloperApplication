package com.example.weatherforcastapp.data.mapper_impl

import com.example.weatherforcastapp.data.mappers.ApiMapper
import com.example.weatherforcastapp.data.models.ApiWeather
import com.example.weatherforcastapp.domain.models.Hourly
import com.example.weatherforcastapp.utils.Util
import com.example.weatherforcastapp.utils.WeatherInfoItem

class ApiHourlyMapper : ApiMapper <Hourly,ApiWeather.ApiHourlyWeather> {

    override fun maptoDomain(apiEntity: ApiWeather.ApiHourlyWeather): Hourly {
        return Hourly (
            temperature = apiEntity.temperature2m,
            time = parseTime ( apiEntity.time ),
            weatherStatus = parseWeatherStatus ( apiEntity.weatherCode )
        )
    }

    private fun parseTime (time : List <Long> ) : List <String> {
        return time.map { Util.formatUnixDate("HH:mm", it ) }
    }

    private fun parseWeatherStatus (code : List <Int> ) : List <WeatherInfoItem> {
        return code.map { Util.getWeatherInfo( it ) }
    }

}