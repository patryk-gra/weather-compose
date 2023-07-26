package com.andromasters.weather.data.remote

import com.squareup.moshi.Json

data class WeatherDto(
    @field:Json(name = "DateTime") val dateTime: String,
    @field:Json(name = "EpochDateTime") val epochDateTime: Long,
    @field:Json(name = "WeatherIcon") val weatherIcon: Int,
    @field:Json(name = "IconPhrase") val iconPhrase: String,
    @field:Json(name = "HasPrecipitation") val hasPrecipitation: Boolean,
    @field:Json(name = "IsDaylight") val isDaylight: Boolean,
    @field:Json(name = "Temperature") val temperature: Temperature,
    @field:Json(name = "RealFeelTemperature") val realFeelTemperature: RealFeelTemperature,
    @field:Json(name = "RealFeelTemperatureShade") val realFeelTemperatureShade: RealFeelTemperature,
    @field:Json(name = "WetBulbTemperature") val wetBulbTemperature: Temperature,
    @field:Json(name = "DewPoint") val dewPoint: Temperature,
    @field:Json(name = "Wind") val wind: Wind,
    @field:Json(name = "WindGust") val windGust: WindGust,
    @field:Json(name = "RelativeHumidity") val relativeHumidity: Int,
    @field:Json(name = "IndoorRelativeHumidity") val indoorRelativeHumidity: Int,
    @field:Json(name = "Visibility") val visibility: Visibility,
    @field:Json(name = "Ceiling") val ceiling: Measurement,
    @field:Json(name = "UVIndex") val uvIndex: Int,
    @field:Json(name = "UVIndexText") val uvIndexText: String,
    @field:Json(name = "PrecipitationProbability") val precipitationProbability: Int,
    @field:Json(name = "ThunderstormProbability") val thunderstormProbability: Int,
    @field:Json(name = "RainProbability") val rainProbability: Int,
    @field:Json(name = "SnowProbability") val snowProbability: Int,
    @field:Json(name = "IceProbability") val iceProbability: Int,
    @field:Json(name = "TotalLiquid") val totalLiquid: Measurement,
    @field:Json(name = "Rain") val rain: Measurement,
    @field:Json(name = "Snow") val snow: Measurement,
    @field:Json(name = "Ice") val ice: Measurement,
    @field:Json(name = "CloudCover") val cloudCover: Int,
    @field:Json(name = "Evapotranspiration") val evapotranspiration: Measurement,
    @field:Json(name = "SolarIrradiance") val solarIrradiance: Measurement,
    @field:Json(name = "MobileLink") val mobileLink: String,
    @field:Json(name = "Link") val link: String
)

data class Temperature(
    @field:Json(name = "Value") val value: Double,
    @field:Json(name = "Unit") val unit: String,
    @field:Json(name = "UnitType") val unitType: Int
)

data class RealFeelTemperature(
    @field:Json(name = "Value") val value: Double,
    @field:Json(name = "Unit") val unit: String,
    @field:Json(name = "UnitType") val unitType: Int,
    @field:Json(name = "Phrase") val phrase: String
)

data class Wind(
    @field:Json(name = "Speed") val speed: Measurement,
)

data class WindGust(
    @field:Json(name = "Speed") val speed: Measurement
)

data class Visibility(
    @field:Json(name = "Value") val value: Double,
    @field:Json(name = "Unit") val unit: String,
    @field:Json(name = "UnitType") val unitType: Int
)

data class Measurement(
    @field:Json(name = "Value") val value: Double,
    @field:Json(name = "Unit") val unit: String,
    @field:Json(name = "UnitType") val unitType: Int
)