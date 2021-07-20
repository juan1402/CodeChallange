package com.smarthings.domain.repository

import com.smarthings.domain.model.WeatherInfo
import com.smarthings.domain.model.Result

interface WeatherRepository {
    suspend fun getWeatherForLocation(location: String): Result<WeatherInfo>
}