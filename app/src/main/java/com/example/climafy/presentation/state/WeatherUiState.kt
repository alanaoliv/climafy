package com.example.climafy.presentation.state

import com.example.climafy.domain.model.Weather

sealed class WeatherUiState {
    object Loading : WeatherUiState()
    data class Success(val weather: Weather) : WeatherUiState()
    data class Error(val message: String) : WeatherUiState()
    object Empty : WeatherUiState()
}
