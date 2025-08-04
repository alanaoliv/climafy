package com.example.climafy.domain.repository

import com.example.climafy.domain.model.Weather
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    suspend fun getWeatherByCity(city: String): Weather
    suspend fun salvarUltimoClima(weather: Weather)
    fun obterUltimoClima(): Flow<Weather?>
}
