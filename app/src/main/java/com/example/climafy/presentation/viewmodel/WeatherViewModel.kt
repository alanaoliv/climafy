package com.example.climafy.presentation.viewmodel

import com.example.climafy.data.local.dao.FavoriteCityDao
import com.example.climafy.data.local.entity.FavoriteCityEntity
import kotlinx.coroutines.flow.first
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.climafy.domain.usecase.GetWeatherUseCase
import com.example.climafy.presentation.state.WeatherUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.example.climafy.domain.repository.FavoriteCityRepository
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlinx.coroutines.flow.collectLatest


@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val getWeatherUseCase: GetWeatherUseCase,
    private val favoriteCityDao: FavoriteCityDao,
    private val favoriteCityRepository: FavoriteCityRepository
) : ViewModel() {

    private val _uiState = mutableStateOf<WeatherUiState>(WeatherUiState.Empty)
    val uiState: State<WeatherUiState> = _uiState

    fun buscarClima(cidade: String) {
        viewModelScope.launch {
            _uiState.value = WeatherUiState.Loading

            try {
                val resultado = getWeatherUseCase(cidade)
                _uiState.value = WeatherUiState.Success(resultado)
            } catch (e: Exception) {
                _uiState.value = WeatherUiState.Error(e.message ?: "Erro desconhecido")
            }
        }
    }
}
