package com.smarthings.data.networking.model

import android.view.View
import com.google.gson.annotations.SerializedName
import com.smarthings.data.database.model.WeatherEntity
import com.smarthings.data.networking.base.RoomMapper

data class WeatherInfoResponse(
    @SerializedName("lat")
    val latitude: Double,
    @SerializedName("long")
    val longitude: Double,
    @SerializedName("timezone")
    val timezone: String,
    @SerializedName("timezone_offset")
    val timeZoneOffset: String,
    @SerializedName("daily")
    val dailyForecast: List<DailyForecast>?
) : RoomMapper<WeatherEntity> {
    override fun mapToRoomEntity() = WeatherEntity(
        View.generateViewId(),
        latitude,
        longitude,
        timezone,
        dailyForecast ?: listOf()
    )
}

data class DailyForecast(
    @SerializedName("dt")
    val unixTimeStamp: Long,
    @SerializedName("sunrise")
    val sunrise: Long,
    @SerializedName("sunset")
    val sunset: Long,
    @SerializedName("weather")
    val weatherInfo: List<WeatherInfo>?,
    @SerializedName("temp")
    val temperatureInfo: TemperatureInfo?
)

data class WeatherInfo(
    @SerializedName("id")
    val id: String,
    @SerializedName("icon")
    val icon: String,
    @SerializedName("main")
    val mainly: String,
    @SerializedName("description")
    val description: String,
)

data class TemperatureInfo(
    @SerializedName("min")
    val minimum: Double,
    @SerializedName("max")
    val maximum: Double
)