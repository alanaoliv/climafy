package com.example.climafy.data.mapper

import com.example.climafy.data.remote.dto.WeatherResponse
import com.example.climafy.domain.model.Weather

fun WeatherResponse.toDomain(): Weather {
    return Weather(
        city = name,
        temperature = main.temp,
        description = weather.firstOrNull()?.description ?: "Descrição não disponível"
    )
}
