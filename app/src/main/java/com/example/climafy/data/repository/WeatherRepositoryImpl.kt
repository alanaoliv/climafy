package com.example.climafy.data.repository

import android.util.Log
import com.example.climafy.BuildConfig
import com.example.climafy.data.local.dao.LastWeatherDao
import com.example.climafy.data.local.entity.LastWeatherEntity
import com.example.climafy.data.mapper.toDomain
import com.example.climafy.data.remote.WeatherApi
import com.example.climafy.domain.model.Weather
import com.example.climafy.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class WeatherRepositoryImpl(
    private val api: WeatherApi,
    private val lastWeatherDao: LastWeatherDao
) : WeatherRepository {

    override suspend fun getWeatherByCity(city: String): Weather {
        return try {
            val response = api.getWeatherByCity(
                city = city,
                apiKey = BuildConfig.OPEN_WEATHER_API_KEY
            )

            if (response.isSuccessful) {
                val body = response.body() ?: throw Exception("Resposta da API veio nula")
                body.toDomain()
            } else {
                Log.e("WeatherRepository", "Erro código: ${response.code()} - ${response.errorBody()?.string()}")
                throw Exception("Erro da API: ${response.code()}")
            }

        } catch (e: Exception) {
            Log.e("WeatherRepository", "Erro real: ${e.message}", e)
            throw e
        }
    }

    override suspend fun salvarUltimoClima(weather: Weather) {
        lastWeatherDao.salvarUltimoClima(weather.toEntity())
    }

    override fun obterUltimoClima(): Flow<Weather?> {
        return lastWeatherDao.obterUltimoClima().map { it?.toDomain() }
    }
}


private fun Weather.toEntity(): LastWeatherEntity {
    return LastWeatherEntity(
        id = 1,
        cityName = city,
        country = country,
        temperature = temperature,
        description = description,
        tempMax = tempMax,
        tempMin = tempMin,
        icon = icon,
        date = getCurrentDate()
    )
}

private fun LastWeatherEntity.toDomain(): Weather {
    return Weather(
        city = cityName,
        country = country,
        temperature = temperature,
        description = description,
        tempMax = tempMax,
        tempMin = tempMin,
        icon = icon,
    )
}

private fun getCurrentDate(): String {
    val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return formatter.format(Date())
}
