package com.smarthings.data.di

import com.smarthings.data.connectivity.Connectivity
import com.smarthings.data.connectivity.ConnectivityImpl
import com.smarthings.data.connectivity.LocationCoder
import com.smarthings.data.connectivity.LocationCoderImpl
import com.smarthings.data.repository.WeatherRepositoryImpl
import com.smarthings.domain.repository.WeatherRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val repositoryModule = module {
    factory<WeatherRepository> { WeatherRepositoryImpl(get(), get(), get(), get(), get()) }
    factory<Connectivity> { ConnectivityImpl(androidContext()) }
    factory<LocationCoder> { LocationCoderImpl(androidContext(), get()) }
}