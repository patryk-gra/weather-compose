package com.andromasters.weather.domain.repository

import com.andromasters.weather.domain.location.LocationData
import com.andromasters.weather.domain.util.Resource
import com.andromasters.weather.domain.weather.WeatherInfo

interface WeatherRepository {
    suspend fun getForecastData(locationData: LocationData): Resource<WeatherInfo>
    suspend fun getLocations(phrase: String): Resource<List<LocationData>>

}