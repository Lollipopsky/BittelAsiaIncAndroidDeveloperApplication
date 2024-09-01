package com.example.weatherforcastapp.data.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiWeather(
    @SerialName("current")
    val current: ApiCurrentWeather,
    @SerialName("current_units")
    val currentUnits: CurrentUnits,
    @SerialName("daily")
    val daily: ApiDailyWeather,
    @SerialName("daily_units")
    val dailyUnits: DailyUnits,
    @SerialName("elevation")
    val elevation: Int,
    @SerialName("generationtime_ms")
    val generationtimeMs: Double,
    @SerialName("hourly")
    val hourly: ApiHourlyWeather,
    @SerialName("hourly_units")
    val hourlyUnits: HourlyUnits,
    @SerialName("latitude")
    val latitude: Double,
    @SerialName("longitude")
    val longitude: Double,
    @SerialName("timezone")
    val timezone: String,
    @SerialName("timezone_abbreviation")
    val timezoneAbbreviation: String,
    @SerialName("utc_offset_seconds")
    val utcOffsetSeconds: Int
) {
    @Serializable
    data class ApiCurrentWeather(
        @SerialName("interval")
        val interval: Int,
        @SerialName("is_day")
        val isDay: Int,
        @SerialName("temperature_2m")
        val temperature2m: Double,
        @SerialName("time")
        val time: Long,
        @SerialName("weather_code")
        val weatherCode: Int,
        @SerialName("wind_direction_10m")
        val windDirection10m: Double,
        @SerialName("wind_speed_10m")
        val windSpeed10m: Double
    )

    @Serializable
    data class CurrentUnits(
        @SerialName("interval")
        val interval: String,
        @SerialName("is_day")
        val isDay: String,
        @SerialName("temperature_2m")
        val temperature2m: String,
        @SerialName("time")
        val time: String,
        @SerialName("weather_code")
        val weatherCode: String,
        @SerialName("wind_direction_10m")
        val windDirection10m: String,
        @SerialName("wind_speed_10m")
        val windSpeed10m: String
    )

    @Serializable
    data class ApiDailyWeather(
        @SerialName("sunrise")
        val sunrise: List<String>,
        @SerialName("sunset")
        val sunset: List<String>,
        @SerialName("temperature_2m_max")
        val temperature2mMax: List<Double>,
        @SerialName("temperature_2m_min")
        val temperature2mMin: List<Double>,
        @SerialName("time")
        val time: List<Long>,
        @SerialName("uv_index_max")
        val uvIndexMax: List<Double>,
        @SerialName("weather_code")
        val weatherCode: List<Int>,
        @SerialName("wind_direction_10m_dominant")
        val windDirection10mDominant: List<Double>,
        @SerialName("wind_speed_10m_max")
        val windSpeed10mMax: List<Double>
    )

    @Serializable
    data class DailyUnits(
        @SerialName("sunrise")
        val sunrise: String,
        @SerialName("sunset")
        val sunset: String,
        @SerialName("temperature_2m_max")
        val temperature2mMax: String,
        @SerialName("temperature_2m_min")
        val temperature2mMin: String,
        @SerialName("time")
        val time: String,
        @SerialName("uv_index_max")
        val uvIndexMax: String,
        @SerialName("weather_code")
        val weatherCode: String,
        @SerialName("wind_direction_10m_dominant")
        val windDirection10mDominant: String,
        @SerialName("wind_speed_10m_max")
        val windSpeed10mMax: String
    )

    @Serializable
    data class ApiHourlyWeather(
        @SerialName("temperature_2m")
        val temperature2m: List<Double>,
        @SerialName("time")
        val time: List<Long>,
        @SerialName("weather_code")
        val weatherCode: List<Int>
    )

    @Serializable
    data class HourlyUnits(
        @SerialName("temperature_2m")
        val temperature2m: String,
        @SerialName("time")
        val time: String,
        @SerialName("weather_code")
        val weatherCode: String
    )
}