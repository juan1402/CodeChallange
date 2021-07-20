package com.smarthings.domain.di

import com.smarthings.domain.interaction.GetWeatherUseCase
import com.smarthings.domain.interaction.GetWeatherUseCaseImpl
import org.koin.dsl.module

val interactionModule = module {
    factory<GetWeatherUseCase> { GetWeatherUseCaseImpl(get()) }
}
