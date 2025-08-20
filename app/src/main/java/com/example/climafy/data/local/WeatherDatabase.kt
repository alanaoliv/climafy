package com.example.climafy.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.climafy.data.local.dao.FavoriteCityDao
import com.example.climafy.data.local.dao.LastWeatherDao
import com.example.climafy.data.local.entity.FavoriteCityEntity
import com.example.climafy.data.local.entity.LastWeatherEntity

@Database(
    entities = [
        FavoriteCityEntity::class, LastWeatherEntity::class],
    version = 2,
    exportSchema = false
)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun favoriteCityDao(): FavoriteCityDao
    abstract fun lastWeatherDao(): LastWeatherDao
}

