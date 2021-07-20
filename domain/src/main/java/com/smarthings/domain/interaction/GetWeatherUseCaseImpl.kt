package com.smarthings.domain.interaction

import com.smarthings.domain.repository.WeatherRepository

class GetWeatherUseCaseImpl(
    private val weatherRepository: WeatherRepository
) : GetWeatherUseCase {
    override suspend fun invoke(location: String) =
        weatherRepository.getWeatherForLocation(location)
}