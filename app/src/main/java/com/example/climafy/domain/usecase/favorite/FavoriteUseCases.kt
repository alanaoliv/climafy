package com.example.climafy.domain.usecase.favorite

data class FavoriteUseCases(
    val inserirCidadeFavoritaUseCase: InserirCidadeFavoritaUseCase,
    val listarCidadesFavoritasUseCase: ListarCidadesFavoritasUseCase,
    val deletarCidadeFavoritaUseCase: DeletarCidadeFavoritaUseCase
)
