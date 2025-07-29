package com.example.climafy.data.repository

import com.example.climafy.data.local.dao.FavoriteCityDao
import com.example.climafy.data.local.entity.FavoriteCityEntity
import com.example.climafy.domain.repository.FavoriteCityRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FavoriteCityRepositoryImpl @Inject constructor(
    private val dao: FavoriteCityDao
) : FavoriteCityRepository {

    override suspend fun inserir(city: FavoriteCityEntity) {
        dao.inserir(city)
    }

    override suspend fun deletar(city: FavoriteCityEntity) {
        dao.deletar(city)
    }

    override fun listarTodas(): Flow<List<FavoriteCityEntity>> {
        return dao.listarTodas()
    }
}