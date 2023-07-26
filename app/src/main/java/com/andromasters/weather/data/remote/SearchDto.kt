package com.andromasters.weather.data.remote

import com.squareup.moshi.Json

data class SearchDto(
    @field:Json(name = "Version") val version: Int,
    @field:Json(name = "Key") val key: String,
    @field:Json(name = "Type") val type: String,
    @field:Json(name = "Rank") val rank: Int,
    @field:Json(name = "LocalizedName") val localizedName: String,
    @field:Json(name = "Country") val country: Country,
    @field:Json(name = "AdministrativeArea") val administrativeArea: AdministrativeArea
)

data class Country(
    @field:Json(name = "ID") val id: String,
    @field:Json(name = "LocalizedName") val localizedName: String
)

data class AdministrativeArea(
    @field:Json(name = "ID") val id: String,
    @field:Json(name = "LocalizedName") val localizedName: String
)