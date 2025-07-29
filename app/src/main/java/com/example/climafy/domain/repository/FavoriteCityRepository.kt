package com.example.climafy.domain.repository

import com.example.climafy.data.local.entity.FavoriteCityEntity
import kotlinx.coroutines.flow.Flow

interface FavoriteCityRepository {
    suspend fun inserir(city: FavoriteCityEntity)
    suspend fun deletar(city: FavoriteCityEntity)
    fun listarTodas(): Flow<List<FavoriteCityEntity>>
}