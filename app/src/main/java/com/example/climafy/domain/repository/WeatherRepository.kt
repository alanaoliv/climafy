package com.example.climafy.domain.repository

import com.example.climafy.data.remote.dto.WeatherResponse

interface WeatherRepository {
    suspend fun getWeatherByCity(city: String): WeatherResponse
}
