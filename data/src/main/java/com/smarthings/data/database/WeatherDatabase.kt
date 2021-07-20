package com.smarthings.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.smarthings.data.database.dao.WeatherDao
import com.smarthings.data.database.model.WeatherEntity

@Database(entities = [WeatherEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
}