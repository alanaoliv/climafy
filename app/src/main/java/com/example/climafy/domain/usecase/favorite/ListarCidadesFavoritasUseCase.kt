package com.example.climafy.domain.usecase.favorite

import com.example.climafy.data.local.entity.FavoriteCityEntity
import com.example.climafy.domain.repository.FavoriteCityRepository
import kotlinx.coroutines.flow.Flow

class ListarCidadesFavoritasUseCase(
    private val repository: FavoriteCityRepository
) {
    operator fun invoke(): Flow<List<FavoriteCityEntity>> {
        return repository.listarTodas()
    }
}