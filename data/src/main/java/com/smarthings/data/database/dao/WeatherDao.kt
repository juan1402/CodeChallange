package com.smarthings.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.smarthings.data.database.WEATHER_TABLE_NAME
import com.smarthings.data.database.model.WeatherEntity

@Dao
interface WeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveWeatherInfo(weather: WeatherEntity)

    @Query("SELECT * FROM $WEATHER_TABLE_NAME WHERE latitude = :latitude AND longitude = :longitude LIMIT 1")
    suspend fun getWeatherInfoForCity(latitude: Double, longitude: Double): WeatherEntity
}