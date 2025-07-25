package com.example.climafy.domain.repository

import com.example.climafy.domain.model.Weather

interface WeatherRepository {
    suspend fun getWeatherByCity(city: String): Weather
}
