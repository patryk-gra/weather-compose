package com.andromasters.weather.data.remote

import com.andromasters.weather.BuildConfig
import okhttp3.logging.HttpLoggingInterceptor

object LoggingInterceptor {

    val INSTANCE: HttpLoggingInterceptor
        get() {
            val httpLoggingInterceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT)

            if (BuildConfig.DEBUG) {
                httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            }

            return httpLoggingInterceptor
        }
}