package com.example.climafy.domain.model

data class Weather(
    val city: String,
    val country: String,
    val temperature: Double,
    val description: String,
    val tempMax: Double?,
    val tempMin: Double?,
    val icon: String
)
