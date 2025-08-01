package com.example.climafy.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.climafy.data.local.entity.FavoriteCityEntity
import com.example.climafy.domain.usecase.favorite.FavoriteUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val favoriteUseCases: FavoriteUseCases
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
}