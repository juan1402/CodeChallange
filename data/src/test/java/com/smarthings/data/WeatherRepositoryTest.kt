package com.smarthings.data

import android.content.Context
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.smarthings.data.common.CoroutineContextProvider
import com.smarthings.data.connectivity.Connectivity
import com.smarthings.data.connectivity.LocationCoder
import com.smarthings.data.database.dao.WeatherDao
import com.smarthings.data.networking.WeatherApi
import com.smarthings.data.repository.WeatherRepositoryImpl
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.core.context.stopKoin
import retrofit2.Response

class WeatherRepositoryTest {

    private val weatherTestApi: WeatherApi = mock()
    private val weatherDao: WeatherDao = mock()
    private val connectivity: Connectivity = mock()
    private val coder: LocationCoder = mock()
    private lateinit var weatherRepository: WeatherRepositoryImpl
    private val coroutineContextProvider: CoroutineContextProvider = mock()
    private var context: Context = mock()

    @Before
    fun setUp() {
        startKoin(context)
        weatherRepository = WeatherRepositoryImpl(
            weatherTestApi,
            weatherDao,
            coder,
            connectivity,
            coroutineContextProvider
        )
    }

    @Test
    fun `test getWeatherForLocation calls api upon success`() {
        runBlocking {
            whenever(coroutineContextProvider.io).thenReturn(mock())
            whenever(coroutineContextProvider.main).thenReturn(mock())
            whenever(coder.search(SAN_SALVADOR_CITY_NAME)).thenReturn(mockLocation)
            whenever(connectivity.hasNetworkAccess()).thenReturn(true)
            whenever(
                weatherTestApi.getWeatherForLocation(
                    mockLocation.latitude,
                    mockLocation.longitude
                )
            )
                .thenReturn(Response.success(successWeatherInfoResponse))

            weatherTestApi.getWeatherForLocation(Double.MIN_VALUE, Double.MIN_VALUE)

            verify(weatherTestApi, times(1))
                .getWeatherForLocation(Double.MIN_VALUE, Double.MIN_VALUE)
        }
    }

    @Test
    fun `test getWeatherForLocation calls api upon failure`() {
        runBlocking {
            whenever(coder.search(SAN_SALVADOR_CITY_NAME)).thenReturn(mockLocation)
            whenever(connectivity.hasNetworkAccess()).thenReturn(true)
            whenever(
                weatherTestApi.getWeatherForLocation(
                    mockLocation.latitude,
                    mockLocation.longitude
                )
            )
                .thenReturn(Response.error(FAKE_FAILURE_ERROR_CODE, failureResponseBody))

            weatherTestApi.getWeatherForLocation(Double.MIN_VALUE, Double.MIN_VALUE)

            verify(weatherTestApi, times(1))
                .getWeatherForLocation(Double.MIN_VALUE, Double.MIN_VALUE)
        }
    }

    @After
    fun tearDow() {
        stopKoin()
    }
}