package com.andromasters.weather.data.mappers

import com.andromasters.weather.data.remote.Temperature
import com.andromasters.weather.data.remote.WeatherDto
import com.andromasters.weather.domain.location.LocationData
import com.andromasters.weather.domain.weather.WeatherData
import com.andromasters.weather.domain.weather.WeatherInfo
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun List<WeatherDto>.toWeatherDataList(): List<WeatherData> {
    return this.mapIndexed { index, item ->
        WeatherData(
            time = LocalDateTime.parse(item.dateTime, DateTimeFormatter.ISO_DATE_TIME),
            temperatureCelsius = getTemperature(item.temperature),
            uvIndex = item.uvIndex,
            windSpeed = item.wind.speed.value,
            windSpeedUnit = item.wind.speed.unit,
            humidity = item.relativeHumidity.toDouble(),
            weatherIcon = item.weatherIcon,
            weatherDesc = item.iconPhrase
        )

    }
}

fun List<WeatherDto>.toWeatherInfo(locationData: LocationData): WeatherInfo {
    val weatherDataPerDay = this.toWeatherDataList()
    return WeatherInfo(
        weatherDataPerHour = weatherDataPerDay.filterIndexed { index, _ -> index != 0 },
        currentWeatherData = weatherDataPerDay[0],
        locationData = locationData
    )
}

fun Double.fahrenheitToCelsius(): Double {
    return (this - 32) * 5 / 9
}

fun Double.kelvinToCelsius(): Double {
    return this - 273.15
}

fun getTemperature(temperature: Temperature) = when (temperature.unit) {
    "F" -> temperature.value.fahrenheitToCelsius()
    "K" -> temperature.value.kelvinToCelsius()
    else -> temperature.value
}

