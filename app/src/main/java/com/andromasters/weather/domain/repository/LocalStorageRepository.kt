package com.andromasters.weather.domain.repository

import com.andromasters.weather.domain.weather.WeatherInfo

interface LocalStorageRepository {
    fun getWeatherInfo(): WeatherInfo?

    fun saveWeatherInfo(weatherInfo: WeatherInfo?)
}