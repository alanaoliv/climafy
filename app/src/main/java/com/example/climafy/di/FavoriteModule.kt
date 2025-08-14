package com.example.climafy.di

import com.example.climafy.data.local.dao.FavoriteCityDao
import com.example.climafy.data.repository.FavoriteCityRepositoryImpl
import com.example.climafy.domain.repository.FavoriteCityRepository
import com.example.climafy.domain.usecase.favorite.DeletarCidadeFavoritaUseCase
import com.example.climafy.domain.usecase.favorite.FavoriteUseCases
import com.example.climafy.domain.usecase.favorite.InserirCidadeFavoritaUseCase
import com.example.climafy.domain.usecase.favorite.ListarCidadesFavoritasUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FavoriteModule {

    @Provides
    @Singleton
    fun provideFavoriteCityRepository(
        dao: FavoriteCityDao
    ): FavoriteCityRepository =
        FavoriteCityRepositoryImpl(dao)

    @Provides
    @Singleton
    fun provideFavoriteUseCases(
        repository: FavoriteCityRepository
    ): FavoriteUseCases =
        FavoriteUseCases(
            inserirCidadeFavoritaUseCase = InserirCidadeFavoritaUseCase(repository),
            listarCidadesFavoritasUseCase = ListarCidadesFavoritasUseCase(repository),
            deletarCidadeFavoritaUseCase = DeletarCidadeFavoritaUseCase(repository)
        )
}
