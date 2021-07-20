@file:Suppress("SpellCheckingInspection")

package com.smarthings.data.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.smarthings.data.networking.model.DailyForecast
import com.smarthings.data.networking.model.TemperatureInfo
import com.smarthings.data.networking.model.WeatherInfo

class Converters {

    private val gson = Gson()

    @TypeConverter
    fun fromDailyForecastToJson(list: List<DailyForecast>?): String {
        return list?.let { gson.toJson(it) } ?: String()
    }

    @TypeConverter
    fun fromJsonToDailyForecast(jsonList: String): List<DailyForecast> {
        val listType = object : TypeToken<List<DailyForecast>>() {}.type
        return gson.fromJson(jsonList, listType)
    }

    @TypeConverter
    fun fromWeatherInfoToJson(list: List<WeatherInfo>?): String {
        return list?.let { gson.toJson(it) } ?: String()
    }

    @TypeConverter
    fun fromJsonToWeatherInfo(jsonList: String): List<WeatherInfo> {
        val listType = object : TypeToken<List<WeatherInfo>>() {}.type
        return gson.fromJson(jsonList, listType)
    }

    @TypeConverter
    fun fromTemperatureInfoToJson(mainInfo: TemperatureInfo?): String {
        return mainInfo?.let { gson.toJson(it) } ?: ""
    }

    @TypeConverter
    fun fromJsonToTemperatureInfo(json: String): TemperatureInfo {
        val type = object : TypeToken<TemperatureInfo>() {}.type
        return gson.fromJson(json, type)
    }
}