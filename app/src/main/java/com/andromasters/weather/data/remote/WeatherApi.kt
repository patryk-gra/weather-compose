package com.andromasters.weather.data.remote

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WeatherApi {
    @GET("forecasts/v1/hourly/12hour/{locationKey}")
    suspend fun getWeatherForecast(
        @Path("locationKey") locationKey: String,
        @Query("apikey") apiKey: String,
        @Query("language") language: String,
        @Query("details") details: Boolean = true,
        @Query("metric") metric: Boolean = true,
    ): List<WeatherDto>

    @GET("locations/v1/cities/autocomplete/")
    suspend fun getLocations(
        @Query("apikey") apiKey: String,
        @Query("q") query: String,
    ): List<SearchDto>
}