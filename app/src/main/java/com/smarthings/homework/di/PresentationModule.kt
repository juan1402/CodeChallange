package com.smarthings.homework.di

import com.smarthings.homework.ui.weather.WeatherViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    viewModel { WeatherViewModel(get()) }
}