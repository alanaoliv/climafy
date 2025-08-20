package com.example.climafy.data.mapper

import com.example.climafy.data.remote.dto.WeatherResponse
import com.example.climafy.domain.model.Weather

fun WeatherResponse.toDomain(): Weather {
    return Weather(
        city = name,
        country = sys.country,
        temperature = main.temp,
        description = weather.firstOrNull()?.description ?: "Descrição não disponível",
        tempMax = main.temp_max,
        tempMin = main.temp_min,
        icon = weather . firstOrNull ()?.icon ?: "",
    )
}
