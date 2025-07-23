package com.example.climafy.domain.usecase

import android.util.Log
import com.example.climafy.data.remote.dto.WeatherResponse
import com.example.climafy.domain.repository.WeatherRepository

class GetWeatherUseCase(
    private val repository: WeatherRepository
) {
    suspend operator fun invoke(city: String): WeatherResponse {
        return try {
            repository.getWeatherByCity(city)
        } catch (e: Exception) {
            Log.e("UseCase", "Erro real: ${e.message}", e)
            throw Exception("Erro ao obter dados do clima")
        }
    }
}