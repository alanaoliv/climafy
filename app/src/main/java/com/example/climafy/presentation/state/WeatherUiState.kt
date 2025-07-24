package com.example.climafy.presentation.state

import com.example.climafy.data.remote.dto.WeatherResponse

data class WeatherUiState(
    val isLoading: Boolean = false,
    val weather: WeatherResponse? = null,
    val error: String? = null
)
