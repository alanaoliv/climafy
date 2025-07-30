package com.example.climafy.domain.usecase.favorite

import com.example.climafy.data.local.entity.FavoriteCityEntity
import com.example.climafy.domain.repository.FavoriteCityRepository

class InserirCidadeFavoritaUseCase(
    private val repository: FavoriteCityRepository
) {
    suspend operator fun invoke(city: FavoriteCityEntity) {
        repository.inserir(city)
    }
}