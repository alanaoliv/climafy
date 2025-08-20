package com.example.climafy.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "last_weather")
data class LastWeatherEntity(
    @PrimaryKey
    val id: Int = 1,
    val cityName: String,
    val country: String,
    val temperature: Double,
    val tempMax: Double?,
    val tempMin: Double?,
    val description: String,
    val icon: String,
    val date: String
)
