package com.andromasters.weather.data.repository

import com.andromasters.weather.BuildConfig
import com.andromasters.weather.data.mappers.toLocationData
import com.andromasters.weather.domain.location.LocationData
import com.andromasters.weather.domain.repository.WeatherRepository
import com.andromasters.weather.domain.util.Resource
import com.andromasters.weather.domain.weather.WeatherInfo
import com.andromasters.weather.data.mappers.toWeatherInfo
import com.andromasters.weather.data.remote.WeatherApi
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val api: WeatherApi
): WeatherRepository {

    override suspend fun getForecastData(locationData: LocationData): Resource<WeatherInfo> {
        return try {
            println("Weather: Get forecast for locationId -> ${locationData.locationId}")
            val data = api.getWeatherForecast(
                locationKey = locationData.locationId,
                apiKey = BuildConfig.API_KEY,
                language = "pl"
            )
            Resource.Success(
                data = data.toWeatherInfo(locationData)
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "An unknown error occurred.")
        }
    }

    override suspend fun getLocations(phrase: String): Resource<List<LocationData>> {
        return try {
            println("Weather: Search for -> $phrase")
            val data = api.getLocations(
                apiKey = BuildConfig.API_KEY,
                query = phrase
            )

            Resource.Success(
                data = data.toLocationData()
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "An unknown error occurred.")
        }
    }
}
