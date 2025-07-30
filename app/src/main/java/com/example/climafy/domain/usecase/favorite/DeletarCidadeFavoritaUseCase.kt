package com.example.climafy.domain.usecase.favorite

import com.example.climafy.data.local.entity.FavoriteCityEntity
import com.example.climafy.domain.repository.FavoriteCityRepository

class DeletarCidadeFavoritaUseCase(
    private val repository: FavoriteCityRepository
) {
    suspend operator fun invoke(city: FavoriteCityEntity) {
        repository.deletar(city)
    }
}