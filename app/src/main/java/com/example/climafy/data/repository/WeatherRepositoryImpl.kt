package com.example.climafy.data.repository

import android.util.Log
import com.example.climafy.BuildConfig
import com.example.climafy.data.remote.WeatherApi
import com.example.climafy.data.remote.dto.WeatherResponse
import com.example.climafy.domain.repository.WeatherRepository

class WeatherRepositoryImpl(
    private val api: WeatherApi
) : WeatherRepository {

    override suspend fun getWeatherByCity(city: String): WeatherResponse {
        return try {
            val response = api.getWeatherByCity(
                city = city,
                apiKey = BuildConfig.OPEN_WEATHER_API_KEY,
                units = "metric",
                language = "pt_br"
            )

            if (response.isSuccessful) {
                response.body() ?: throw Exception("Resposta da API veio nula")
            } else {
                // Log detalhado do erro
                Log.e("WeatherRepository", "Erro código: ${response.code()}")
                Log.e("WeatherRepository", "Erro body: ${response.errorBody()?.string()}")
                throw Exception("Erro da API: ${response.code()}")
            }

        } catch (e: Exception) {
            Log.e("WeatherRepository", "Erro real: ${e.message}", e)
            throw Exception("Erro ao obter dados do clima")
        }
    }
}