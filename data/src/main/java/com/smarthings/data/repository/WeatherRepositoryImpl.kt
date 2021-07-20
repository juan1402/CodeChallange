package com.smarthings.data.repository

import com.smarthings.data.common.CoroutineContextProvider
import com.smarthings.data.connectivity.Connectivity
import com.smarthings.data.connectivity.LocationCoder
import com.smarthings.data.database.DB_ENTRY_ERROR
import com.smarthings.data.database.dao.WeatherDao
import com.smarthings.data.networking.WeatherApi
import com.smarthings.data.networking.base.DomainMapper
import com.smarthings.data.networking.base.getData
import com.smarthings.domain.model.*
import com.smarthings.domain.repository.WeatherRepository
import kotlinx.coroutines.withContext

class WeatherRepositoryImpl(
    private val weatherApi: WeatherApi,
    private val weatherDao: WeatherDao,
    private val locationCoder: LocationCoder,
    private val connectivity: Connectivity,
    private val contextProvider: CoroutineContextProvider
) : WeatherRepository {

    override suspend fun getWeatherForLocation(location: String): Result<WeatherInfo> {
        val coderLocation = locationCoder.search(location)
        return fetchData(
            apiDataProvider = {
                weatherApi.getWeatherForLocation(
                    coderLocation.latitude,
                    coderLocation.longitude
                ).getData(
                    fetchFromCacheAction = {
                        weatherDao.getWeatherInfoForCity(
                            coderLocation.latitude,
                            coderLocation.longitude
                        )
                    },
                    cacheAction = { weatherDao.saveWeatherInfo(it) })
            },
            dbDataProvider = {
                weatherDao.getWeatherInfoForCity(
                    coderLocation.latitude,
                    coderLocation.longitude
                )
            }
        )
    }

    /**
     * Use this if you need to cache data after fetching it from the api,
     * or retrieve something from cache
     */
    private suspend fun <T : Any, R: DomainMapper<T>> fetchData(
        apiDataProvider: suspend () -> Result<T>,
        dbDataProvider: suspend () -> R?
    ): Result<T> {
        return if (connectivity.hasNetworkAccess()) {
            withContext(contextProvider.io) {
                apiDataProvider()
            }
        } else {
            withContext(contextProvider.io) {
                val dbResult = dbDataProvider()
                if (dbResult != null) Success(dbResult.mapToDomainModel())
                else Failure(HttpError(Throwable(DB_ENTRY_ERROR)))
            }
        }
    }
}