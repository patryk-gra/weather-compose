package com.andromasters.weather.presentation.forecast

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andromasters.weather.domain.location.LocationData
import com.andromasters.weather.domain.repository.WeatherRepository
import com.andromasters.weather.domain.usecase.GetSavedWeatherUseCase
import com.andromasters.weather.domain.usecase.SaveWeatherUseCase
import com.andromasters.weather.domain.util.Resource
import com.andromasters.weather.domain.weather.WeatherInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: WeatherRepository,
    private val savedWeatherUseCase: SaveWeatherUseCase,
    private val getSavedWeatherUseCase: GetSavedWeatherUseCase
): ViewModel() {

    var state by mutableStateOf(WeatherState())
        private set

    private var savedWeatherInfo: WeatherInfo? = null

    init {
        val weatherInfo = getSavedWeatherUseCase()
        weatherInfo?.let {
            savedWeatherInfo = it
        }
    }

    fun resetForecastData() {
        savedWeatherUseCase(null)
    }

    fun loadWeatherInfo(locationId: String, locationName: String) {
        if (savedWeatherInfo?.currentWeatherData?.time == null || LocalDateTime.now().isAfter(savedWeatherInfo?.currentWeatherData?.time)) {
            println("Get Weather Info from API")
            viewModelScope.launch {
                state = state.copy(
                    isLoading = true,
                    error = null
                )
                when (val result = repository.getForecastData(LocationData(locationId, locationName))) {
                    is Resource.Success -> {
                        state = state.copy(
                            weatherInfo = result.data,
                            isLoading = false,
                            error = null
                        )
                        savedWeatherUseCase(result.data)
                    }
                    is Resource.Error -> {
                        state = state.copy(
                            weatherInfo = null,
                            isLoading = false,
                            error = result.message
                        )
                    }
                }
            }
        } else {
            println("Get Weather Info from data storage")
            state = state.copy(
                weatherInfo = savedWeatherInfo,
                isLoading = false,
                error = null
            )
        }
    }
}
