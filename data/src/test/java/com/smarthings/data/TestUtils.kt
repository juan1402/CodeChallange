package com.smarthings.data

import android.content.Context
import com.smarthings.data.common.CoroutineContextProvider
import com.smarthings.data.connectivity.Location
import com.smarthings.data.di.databaseModule
import com.smarthings.data.di.networkingModule
import com.smarthings.data.di.repositoryModule
import com.smarthings.data.networking.model.WeatherInfoResponse
import com.smarthings.domain.di.interactionModule
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

const val SAN_SALVADOR_CITY_NAME = "San Salvador"
const val FAKE_FAILURE_ERROR_CODE = 400

val successWeatherInfoResponse =
    WeatherInfoResponse(Double.MIN_VALUE, Double.MIN_VALUE, String(), String(), listOf())
val failureResponseBody = ResponseBody.create("text".toMediaTypeOrNull(), "network error")
val mockLocation = Location(Double.MIN_VALUE, Double.MIN_VALUE, String())

fun startKoin(context: Context) {
    val appModule = listOf(module { single { CoroutineContextProvider() } })
    val domainModules = listOf(interactionModule)
    val dataModules = listOf(networkingModule, repositoryModule, databaseModule)
    org.koin.core.context.startKoin {
        androidContext(context)
        modules(appModule + domainModules + dataModules)
    }
}