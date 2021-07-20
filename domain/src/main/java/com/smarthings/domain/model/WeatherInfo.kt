package com.smarthings.domain.model

data class WeatherInfo(
    val latitude: Double,
    val longitude: Double,
    val timeZone: String,
    val dailyInfo: List<DailyInfo>
)

data class DailyInfo(
    val timeStamp: Long,
    val sunrise: Long,
    val sunset: Long,
    val icon: String,
    val mainly: String,
    val minimum: Double,
    val maximum: Double
)