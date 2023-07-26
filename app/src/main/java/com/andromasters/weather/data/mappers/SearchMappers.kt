package com.andromasters.weather.data.mappers

import com.andromasters.weather.domain.location.LocationData
import com.andromasters.weather.data.remote.SearchDto

fun List<SearchDto>.toLocationData(): List<LocationData> {
    return this.map {
        LocationData(
            locationId = it.key,
            name = it.localizedName
        )
    }
}