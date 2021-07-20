package com.smarthings.homework.ui.weather

import com.smarthings.domain.interaction.GetWeatherUseCase
import com.smarthings.domain.model.WeatherInfo
import com.smarthings.domain.model.onFailure
import com.smarthings.domain.model.onSuccess
import com.smarthings.homework.ui.base.BaseViewModel
import com.smarthings.homework.ui.base.Error
import com.smarthings.homework.ui.base.Success

class WeatherViewModel(
    private val getWeather: GetWeatherUseCase
) : BaseViewModel<WeatherInfo>() {

    private fun getWeatherForLocation(
        location: String = DEFAULT_CITY_NAME
    ) = executeUseCase {
        getWeather(location)
            .onSuccess {
                _viewState.value = Success(it)
            }
            .onFailure {
                _viewState.value = Error(it.throwable)
            }
    }

    fun searchButtonAction(location: String) {
        if (location.isNotEmpty()) getWeatherForLocation(location)
        else getWeatherForLocation()
    }

    companion object {
        private const val DEFAULT_CITY_NAME = "San Salvador"
    }
}