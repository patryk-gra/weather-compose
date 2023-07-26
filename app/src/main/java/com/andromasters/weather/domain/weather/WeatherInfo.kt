package com.andromasters.weather.domain.weather

import com.andromasters.weather.domain.location.LocationData
import com.andromasters.weather.domain.weather.WeatherData

data class WeatherInfo(
    val weatherDataPerHour: List<WeatherData>? = null,
    val currentWeatherData: WeatherData? = null,
    val locationData: LocationData? = null
)
