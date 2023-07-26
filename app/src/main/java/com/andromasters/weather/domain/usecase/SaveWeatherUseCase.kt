package com.andromasters.weather.domain.usecase

import com.andromasters.weather.domain.repository.LocalStorageRepository
import com.andromasters.weather.domain.weather.WeatherInfo
import javax.inject.Inject

class SaveWeatherUseCase @Inject constructor(
    private val localStorageRepository: LocalStorageRepository
) {
    operator fun invoke(
        weatherInfo: WeatherInfo?
    ) {
        localStorageRepository.saveWeatherInfo(weatherInfo)
    }
}