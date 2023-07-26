package com.andromasters.weather.data.repository

import android.content.Context
import com.andromasters.weather.data.utils.GsonUtils
import com.andromasters.weather.domain.repository.LocalStorageRepository
import com.andromasters.weather.domain.weather.WeatherInfo

class LocalStorageRepositoryImpl(context: Context) : LocalStorageRepository {

    companion object {
        private const val PREFS_NAME = "WeatherAppPrefs"
        private const val WEATHER_INFO = "weather_info"
    }

    private val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    override fun getWeatherInfo(): WeatherInfo? {
        val json = sharedPreferences.getString(WEATHER_INFO, null)
        return if (json != null) {
            GsonUtils.fromJson(json, WeatherInfo::class.java)
        } else {
            null
        }
    }

    override fun saveWeatherInfo(weatherInfo: WeatherInfo?) {
        val json = GsonUtils.toJson(weatherInfo)
        sharedPreferences.edit().putString(WEATHER_INFO, json).apply()
    }
}