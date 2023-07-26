package com.andromasters.weather.domain.weather

import androidx.annotation.DrawableRes
import com.andromasters.weather.R

/**
 * Documentation
 * https://developer.accuweather.com/weather-icons
 */
sealed class WeatherType(
    @DrawableRes val iconRes: Int
) {
    object Sunny : WeatherType(
        iconRes = R.drawable.ic_sunny
    )

    object PartlySunny : WeatherType(
        iconRes = R.drawable.ic_partly_sunny
    )

    object Cloudy : WeatherType(
        iconRes = R.drawable.ic_cloudy
    )

    object VeryCloudy : WeatherType(
        iconRes = R.drawable.ic_very_cloudy
    )

    object Rainy : WeatherType(
        iconRes = R.drawable.ic_rainshower
    )

    object SnowyRain : WeatherType(
        iconRes = R.drawable.ic_snowyrainy
    )

    object Snowy: WeatherType(
        iconRes = R.drawable.ic_snowy
    )
    object HeavySnow: WeatherType(
        iconRes = R.drawable.ic_heavysnow
    )

    object Thunder: WeatherType(
        iconRes = R.drawable.ic_thunder
    )
    object RainyThunder: WeatherType(
        iconRes = R.drawable.ic_rainythunder
    )

    companion object {
        fun fromWMO(code: Int?): WeatherType {
            return when(code) {
                in 1..2 -> Sunny
                in 3 .. 4 -> PartlySunny
                6 -> Cloudy
                in 7 .. 11 -> VeryCloudy
                in 12 .. 14 -> RainyThunder
                in 15 .. 17 -> Thunder
                18 -> Rainy
                in 19 .. 25 -> Snowy
                in 26.. 29 -> SnowyRain
                in 32 .. 38 -> Cloudy //TODO use proper icons in future
                in 39 .. 40 -> Rainy
                in 41 .. 42 -> Thunder
                in 43 .. 44 -> HeavySnow
                else -> Sunny
            }
        }
    }
}