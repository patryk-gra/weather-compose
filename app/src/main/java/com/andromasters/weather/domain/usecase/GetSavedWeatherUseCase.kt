package com.andromasters.weather.domain.usecase

import com.andromasters.weather.domain.repository.LocalStorageRepository
import com.andromasters.weather.domain.weather.WeatherInfo
import javax.inject.Inject

class GetSavedWeatherUseCase @Inject constructor(
    private val localStorageRepository: LocalStorageRepository
) {
    operator fun invoke(): WeatherInfo? {
        return localStorageRepository.getWeatherInfo()
    }
}