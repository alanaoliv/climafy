package com.example.climafy.data.remote.dto

data class WeatherResponse(
    val name: String,
    val weather: List<Weather>,
    val main: Main,
    val wind: Wind
)

data class Weather(
    val description: String,
    val icon: String
)

data class Main(
    val temp: Double,
    val humidity: Int
)

data class Wind(
    val speed: Double
)