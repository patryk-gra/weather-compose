package com.andromasters.weather.domain.weather

import java.time.LocalDateTime

data class WeatherData(
    val time: LocalDateTime?,
    val temperatureCelsius: Double?,
    val uvIndex: Int?,
    val windSpeed: Double?,
    val windSpeedUnit: String?,
    val humidity: Double?,
    val weatherIcon: Int?,
    val weatherDesc: String?
)
