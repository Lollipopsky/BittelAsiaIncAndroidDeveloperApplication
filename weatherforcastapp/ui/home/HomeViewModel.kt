package com.example.weatherforcastapp.ui.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherforcastapp.domain.models.Daily
import com.example.weatherforcastapp.domain.models.Weather
import com.example.weatherforcastapp.domain.repository.WeatherRepository
import com.example.weatherforcastapp.utils.Response
import com.example.weatherforcastapp.utils.Util
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel

class HomeViewModel @Inject constructor(
    private val repository: WeatherRepository

) : ViewModel (){
    var homeState by mutableStateOf ( HomeState() )
        private set

    init {
        viewModelScope.launch {
            repository.getWeatherData(latitude.toFloat(), longitude.toFloat()).collect { response ->
                when (response) {
                    is Response.Loading -> {
                        homeState = homeState.copy (
                            isLoading = true
                        )
                    }

                    is Response.Success -> {
                        homeState = homeState.copy (
                            isLoading = true,
                            error = null,
                            weather = response.data
                        )
                        val todayDailyWeatherInfo = response.data?.daily?.weatherInfo?.find {
                            Util.isTodayDate ( it.time )
                        }

                        homeState = homeState.copy (
                            dailyWeaherInfo = todayDailyWeatherInfo
                        )
                    }

                    is Response.Error -> {
                        homeState = homeState.copy (
                            isLoading = false,
                            error = response.message
                        )
                    }

                }
            }
        }
    }


}

data class HomeState (
    val weather : Weather? = null,
    val error : String? = null,
    val isLoading : Boolean = false,
    val dailyWeaherInfo : Daily.WeatherInfo? = null
)