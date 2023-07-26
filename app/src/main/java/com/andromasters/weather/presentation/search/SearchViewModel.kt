package com.andromasters.weather.presentation.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andromasters.weather.domain.repository.WeatherRepository
import com.andromasters.weather.domain.usecase.GetSavedWeatherUseCase
import com.andromasters.weather.domain.util.Resource
import com.andromasters.weather.domain.weather.WeatherInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: WeatherRepository,
    getSavedWeatherUseCase: GetSavedWeatherUseCase
) : ViewModel() {

    var state by mutableStateOf(SearchState())
        private set

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    var currentWeatherInfo by mutableStateOf(WeatherInfo())
        private set

    init {
        val weatherInfo = getSavedWeatherUseCase()
        weatherInfo?.let {
            currentWeatherInfo = it
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    val locations = searchText
        .debounce(1000L)
        .onEach { setLoading(true) }
        .flatMapLatest { text ->
            if (text.isBlank()) {
                flowOf(emptyList())
            } else {
                flow {
                    println("Weather: Search text: $text")
                    val result = repository.getLocations(text)
                    if (result is Resource.Error) {
                        setError(result.message ?: "")
                    }
                    emit(result.data)
                }.onStart { delay(2000L) }
            }
        }
        .onEach { setLoading(false) }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    fun onSearchTextChange(text: String) {
        state = state.copy(
            searchText = text
        )
        _searchText.value = text
    }

    private fun setLoading(value: Boolean) {
        state = state.copy(
            isLoading = value
        )
    }

    private fun setError(value: String) {
        state = state.copy(
            isLoading = false,
            error = value
        )
    }
}