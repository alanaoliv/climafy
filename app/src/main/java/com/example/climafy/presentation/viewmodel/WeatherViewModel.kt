package com.example.climafy.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.climafy.domain.usecase.GetWeatherUseCase
import com.example.climafy.presentation.state.WeatherUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val getWeatherUseCase: GetWeatherUseCase
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
