package com.example.weatherforcastapp.data.mapper_impl

import com.example.weatherforcastapp.data.mappers.ApiMapper
import com.example.weatherforcastapp.data.models.ApiWeather
import com.example.weatherforcastapp.dependencies.ApiCurrentWeatherMapperAnnotation
import com.example.weatherforcastapp.dependencies.ApiDailyMapperAnnotation
import com.example.weatherforcastapp.dependencies.ApiHourlyWeatherMapperAnnotation
import com.example.weatherforcastapp.domain.models.CurrentWeather
import com.example.weatherforcastapp.domain.models.Daily
import com.example.weatherforcastapp.domain.models.Hourly
import com.example.weatherforcastapp.domain.models.Weather
import javax.inject.Inject

class ApiWeatherMapper @Inject constructor(
    @ApiDailyMapperAnnotation private val apiDailyMapper : ApiMapper <Daily, ApiWeather.ApiDailyWeather>,
    @ApiCurrentWeatherMapperAnnotation private val apiCurrentWeatherMapper : ApiMapper <CurrentWeather, ApiWeather.ApiCurrentWeather>,
    @ApiHourlyWeatherMapperAnnotation val apiHourlyMapper: ApiMapper <Hourly, ApiWeather.ApiHourlyWeather>,

    ): ApiMapper < Weather, ApiWeather> {

    override fun maptoDomain(apiEntity: ApiWeather): Weather {
        return Weather(
            currentWeather = apiCurrentWeatherMapper.maptoDomain ( apiEntity.current ),
            daily = apiDailyMapper.maptoDomain( apiEntity.daily ),
            hourly = apiHourlyMapper.maptoDomain( apiEntity.hourly )
        )
    }
}