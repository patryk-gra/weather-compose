package com.andromasters.weather.presentation.search

import com.andromasters.weather.domain.location.LocationData

data class SearchState (
    val locationDataList: List<LocationData>? = null,
    val searchText: String? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)