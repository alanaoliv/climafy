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
import com.example.climafy.domain.model.Weather
import com.example.climafy.domain.repository.FavoriteCityRepository
import com.example.climafy.domain.repository.WeatherRepository
import com.example.climafy.domain.usecase.favorite.FavoriteUseCases
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlinx.coroutines.flow.collectLatest
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val getWeatherUseCase: GetWeatherUseCase,
    private val favoriteUseCases: FavoriteUseCases,
    private val favoriteCityRepository: FavoriteCityRepository,
    private var weatherRepository: WeatherRepository
) : ViewModel() {

    private val _uiState = mutableStateOf<WeatherUiState>(WeatherUiState.Empty)
    val uiState: State<WeatherUiState> = _uiState

    fun buscarClima(cidade: String) {
        viewModelScope.launch {
            _uiState.value = WeatherUiState.Loading

            try {
                val resultado = getWeatherUseCase(cidade)
                _uiState.value = WeatherUiState.Success(resultado)

                weatherRepository.salvarUltimoClima(resultado)

            } catch (e: Exception) {

                weatherRepository.obterUltimoClima().collectLatest { weather ->
                    weather?.let {

                        Log.d("Offline", "Mostrando clima salvo: ${weather?.city}")

                        _uiState.value = WeatherUiState.Success(it)

                    } ?: run {
                        _uiState.value = WeatherUiState.Error(e.message ?: "Erro ${e.message}")
                    }
                }
            }
        }
    }


    fun favoritarCidade(weather: Weather) {
        viewModelScope.launch {
            val favoriteCity = FavoriteCityEntity(
                cityName = weather.city,
                country = weather.country,
                temperature = weather.temperature,
                description = weather.description,
                icon = weather.icon,
                date = getCurrentDate()
            )
            favoriteCityRepository.inserir(favoriteCity)
            Log.d("WeatherViewModel", "Cidade favoritada: ${favoriteCity.cityName}")        }
    }

    fun listarFavoritosLog() {
        viewModelScope.launch {
            favoriteCityRepository.listarTodas().collect { lista ->
                Log.d("WeatherViewModel", "Listando cidades favoritas:")
                lista.forEach {
                    Log.d("WeatherViewModel", "→ ${it.cityName}, ${it.temperature}°C")
                }
            }
        }
    }

    private fun getCurrentDate(): String {
        val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return formatter.format(Date())
    }
}

