package com.example.climafy.domain.usecase

import com.example.climafy.domain.model.Weather
import com.example.climafy.domain.repository.WeatherRepository

class GetWeatherUseCase(
    private val repository: WeatherRepository
) {
    suspend operator fun invoke(city: String): Weather {
        return repository.getWeatherByCity(city)
    }
}