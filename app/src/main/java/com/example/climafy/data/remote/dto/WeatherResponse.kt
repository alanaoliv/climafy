package com.example.climafy.data.remote.dto

data class WeatherResponse(
    val name: String,
    val weather: List<WeatherDto>,
    val main: Main,
    val wind: Wind,
    val sys: SysDto
)

data class WeatherDto(
    val description: String,
    val icon: String
)

data class Main(
    val temp: Double,
    val temp_min: Double,
    val temp_max: Double,
    val humidity: Int
)

data class Wind(
    val speed: Double
)

data class SysDto(
    val country: String
)
