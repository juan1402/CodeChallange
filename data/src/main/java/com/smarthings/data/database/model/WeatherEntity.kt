package com.smarthings.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.smarthings.data.database.WEATHER_TABLE_NAME
import com.smarthings.data.networking.base.DomainMapper
import com.smarthings.data.networking.model.DailyForecast
import com.smarthings.domain.model.DailyInfo
import com.smarthings.domain.model.WeatherInfo

@Entity(tableName = WEATHER_TABLE_NAME)
data class WeatherEntity(
    @PrimaryKey val id: Int? = 0,
    val latitude: Double,
    val longitude: Double,
    val timeZone: String,
    val dailyForecast: List<DailyForecast>
) : DomainMapper<WeatherInfo> {

    override fun mapToDomainModel() = WeatherInfo(
        latitude,
        longitude,
        timeZone,
        dailyForecast.map { dailyForecast ->
            DailyInfo(
                dailyForecast.unixTimeStamp,
                dailyForecast.sunrise,
                dailyForecast.sunset,
                dailyForecast.weatherInfo?.firstOrNull()?.icon ?: String(),
                dailyForecast.weatherInfo?.firstOrNull()?.mainly ?: String(),
                dailyForecast.temperatureInfo?.minimum ?: Double.MIN_VALUE,
                dailyForecast.temperatureInfo?.maximum ?: Double.MAX_VALUE
            )
        }
    )
}