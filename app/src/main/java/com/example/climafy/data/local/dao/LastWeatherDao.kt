package com.example.climafy.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.climafy.data.local.entity.LastWeatherEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LastWeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun salvarUltimoClima(lastWeather: LastWeatherEntity)

    @Query("SELECT * FROM last_weather WHERE id = 1")
    fun obterUltimoClima(): Flow<LastWeatherEntity?>
}