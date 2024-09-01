package com.example.weatherforcastapp.data.remote

import android.hardware.camera2.CameraExtensionSession.StillCaptureLatency
import android.icu.util.TimeZone
import com.example.weatherforcastapp.data.models.ApiWeather
import com.example.weatherforcastapp.utils.ApiForecast
import com.example.weatherforcastapp.utils.ApiParameters
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET(ApiForecast.END_POINT)
    suspend fun getWeatherData(
        @Query(ApiParameters.LATITUDE) latitude: Float = -6.8f,
        @Query(ApiParameters.LONGITUDE) longitude: Float = 39.28f,
        @Query(ApiParameters.DAILY) daily: Array<String> = arrayOf(
            "weather_code",
            "temperature_2m_max",
            "temperature_2m_min",
            "wind_speed_10m_max",
            "wind_direction_10_dominant",
            "sunrise",
            "sunset",
            "uv_index_max"
        ),
        @Query(ApiParameters.CURRENT_WEATHER) currentWeather: Array<String> = arrayOf(
            "temperature_2m",
            "is_day",
            "weather_speed_10m",
            "wind_direction_10m",
        ),
        @Query(ApiParameters.HOURLY) hourlyWeather: Array<String> = arrayOf(
            "weather_code",
            "temperature_2m",
        ),
        @Query(ApiParameters.TIME_FORMAT) timeFormat: String = "unixtime",
        @Query(ApiParameters.TIMEZONE) timeZone: String = "Philippines",
    ): ApiWeather
}