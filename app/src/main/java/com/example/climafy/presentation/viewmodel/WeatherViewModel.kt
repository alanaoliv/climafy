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
import kotlinx.coroutines.flow.firstOrNull
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

    private val _mensagemErro = mutableStateOf<String?>(null)
    val mensagemErro: State<String?> = _mensagemErro

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
                Log.e("WeatherViewModel", "Erro ao buscar clima: ${e.message}", e)

                val weather = try {
                    weatherRepository.obterUltimoClima().firstOrNull()
                } catch (ex: Exception) {
                    null
                }

                if (weather != null) {
                    Log.d("Offline", "Mostrando clima salvo: ${weather.city}")
                    _uiState.value = WeatherUiState.Success(weather)

                    val mensagemClara = tratarMensagemDeErro(e)
                    _mensagemErro.value = mensagemClara

                } else {
                    val mensagemClara = tratarMensagemDeErro(e)
                    _uiState.value = WeatherUiState.Error(mensagemClara)
                    _mensagemErro.value = mensagemClara
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

    private fun tratarMensagemDeErro(e: Exception): String {
        val mensagem = e.message?.lowercase() ?: ""

        return when {
            mensagem.contains("unable to resolve host") ||
                    mensagem.contains("failed to connect") ||
                    mensagem.contains("no address associated") -> {
                "Sem conexão com a internet. Verifique sua rede."
            }

            mensagem.contains("404") || mensagem.contains("not found") -> {
                "Cidade não encontrada. Verifique o nome digitado."
            }

            mensagem.contains("timeout") -> {
                "Tempo de conexão esgotado. Tente novamente."
            }

            else -> {
                "Erro ao buscar clima. Tente novamente mais tarde."
            }
        }
    }


    fun resetarMensagemErro() {
        _mensagemErro.value = null
    }

}

