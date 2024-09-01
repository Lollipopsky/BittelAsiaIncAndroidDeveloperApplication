package com.example.weatherforcastapp.dependencies

import com.example.weatherforcastapp.data.mapper_impl.ApiDailyMapper
import com.example.weatherforcastapp.data.mapper_impl.ApiHourlyMapper
import com.example.weatherforcastapp.data.mapper_impl.ApiWeatherMapper
import com.example.weatherforcastapp.data.mapper_impl.CurrentWeatherMapper
import com.example.weatherforcastapp.data.mappers.ApiMapper
import com.example.weatherforcastapp.data.models.ApiWeather
import com.example.weatherforcastapp.domain.models.CurrentWeather
import com.example.weatherforcastapp.domain.models.Daily
import com.example.weatherforcastapp.domain.models.Hourly
import com.example.weatherforcastapp.domain.models.Weather
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier


@Module
@InstallIn (SingletonComponent :: class)

object MapperModule {

    @ApiDailyMapperAnnotation
    @Provides
    fun provideDailyApi() : ApiMapper <Daily, ApiWeather.ApiDailyWeather> {
        return ApiDailyMapper()

    }

    @ApiWeatherMapperAnnotation
    @Provides
    fun provideApiWeatherMapper(
        apiDailyMapper : ApiMapper <Daily, ApiWeather.ApiDailyWeather>,
        apiCurrentWeatherMapper : ApiMapper <CurrentWeather, ApiWeather.ApiCurrentWeather>,
        apiHourlyMapper : ApiMapper <Hourly, ApiWeather.ApiHourlyWeather>,

        ) : ApiMapper <Weather, ApiWeather> {
        return ApiWeatherMapper ( apiDailyMapper, apiCurrentWeatherMapper, apiHourlyMapper )

    }

    @ApiCurrentWeatherMapperAnnotation
    @Provides
    fun provideCurrentWeatherMapper() : ApiMapper <CurrentWeather, ApiWeather.ApiCurrentWeather> {
        return CurrentWeatherMapper()

    }

    @ApiHourlyWeatherMapperAnnotation
    @Provides
    fun provideHourlyApi() : ApiMapper<Hourly, ApiWeather.ApiHourlyWeather> {
        return ApiHourlyMapper()

    }

}

@Qualifier
@Retention (AnnotationRetention.BINARY)
annotation class ApiDailyMapperAnnotation

@Qualifier
@Retention (AnnotationRetention.BINARY)
annotation class ApiWeatherMapperAnnotation

@Qualifier
@Retention (AnnotationRetention.BINARY)
annotation class ApiCurrentWeatherMapperAnnotation

@Qualifier
@Retention (AnnotationRetention.BINARY)
annotation class ApiHourlyWeatherMapperAnnotation