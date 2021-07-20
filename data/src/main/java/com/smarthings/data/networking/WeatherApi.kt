@file:Suppress("SpellCheckingInspection")

package com.smarthings.data.networking

import com.smarthings.data.BuildConfig
import com.smarthings.data.networking.model.WeatherInfoResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("onecall")
    suspend fun getWeatherForLocation(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("appid") apiKey: String = BuildConfig.API_KEY,
        @Query("exclude") exclude: String = "minutely,hourly,alerts,current"
    ): Response<WeatherInfoResponse>
}