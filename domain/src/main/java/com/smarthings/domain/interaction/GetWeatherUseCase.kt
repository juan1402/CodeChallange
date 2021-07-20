package com.smarthings.domain.interaction

import com.smarthings.domain.model.WeatherInfo
import com.smarthings.domain.model.Result

interface GetWeatherUseCase {
   suspend operator fun invoke(location: String): Result<WeatherInfo>
}