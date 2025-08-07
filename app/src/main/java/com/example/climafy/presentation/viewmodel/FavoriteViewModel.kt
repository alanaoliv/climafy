package com.example.climafy.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.climafy.data.local.entity.FavoriteCityEntity
import com.example.climafy.domain.usecase.GetWeatherUseCase
import com.example.climafy.domain.usecase.favorite.FavoriteUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlinx.coroutines.flow.first

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val favoriteUseCases: FavoriteUseCases,
    private val getWeatherUseCase: GetWeatherUseCase
) : ViewModel() {

    val favoritos = favoriteUseCases
        .listarCidadesFavoritasUseCase()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun deletarCidade(cidade: FavoriteCityEntity) {
        viewModelScope.launch {
            favoriteUseCases.deletarCidadeFavoritaUseCase(cidade)
            Log.d("FavoriteViewModel", "Cidade deletada: ${cidade.cityName}")
        }
    }

    fun favoritarCidade(cidade: FavoriteCityEntity) {
        viewModelScope.launch {
            favoriteUseCases.inserirCidadeFavoritaUseCase(cidade)
            Log.d("FavoriteViewModel", "Cidade favoritada: ${cidade.cityName}")
        }
    }

    init {
        viewModelScope.launch {
            val listaInicial = favoriteUseCases.listarCidadesFavoritasUseCase().first() // Pega apenas o primeiro valor (a lista atual)
            listaInicial.forEach { cidade ->
                try {
                    val novoClima = getWeatherUseCase(cidade.cityName)
                    val cidadeAtualizada = cidade.copy(
                        temperature = novoClima.temperature,
                        description = novoClima.description,
                        icon = novoClima.icon,
                        date = getCurrentDate()
                    )
                    favoriteUseCases.inserirCidadeFavoritaUseCase(cidadeAtualizada)
                    Log.d("FavoriteViewModel", "Atualizado ao iniciar: ${cidade.cityName}")
                } catch (e: Exception) {
                    Log.e("FavoriteViewModel", "Erro ao atualizar ${cidade.cityName} ao iniciar", e)
                }
            }
        }
    }


    private fun getCurrentDate(): String {
        val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return formatter.format(Date())
    }
}