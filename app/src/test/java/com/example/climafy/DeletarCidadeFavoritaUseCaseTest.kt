package com.example.climafy

import com.example.climafy.data.local.entity.FavoriteCityEntity
import com.example.climafy.domain.repository.FavoriteCityRepository
import com.example.climafy.domain.usecase.favorite.DeletarCidadeFavoritaUseCase
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import io.mockk.just

class DeletarCidadeFavoritaUseCaseTest {

    private lateinit var repository: FavoriteCityRepository
    private lateinit var useCase: DeletarCidadeFavoritaUseCase

    @Before
    fun setup() {
        repository = mockk(relaxed = true)
        useCase = DeletarCidadeFavoritaUseCase(repository)
    }

    @Test
    fun `deve chamar o repository para deletar a cidade`() = runTest {
        val cidade = FavoriteCityEntity(
            cityName = "Recife",
            id = 0,
            country = "BR",
            temperature = 27.0,
            description = "Ensolarado",
            icon = "03d",
            date = "2023-08-01",
        )

        coEvery { repository.deletar(cidade) } just Runs

        val useCase = DeletarCidadeFavoritaUseCase(repository)

        useCase(cidade)

        coVerify { repository.deletar(cidade) }
    }
}
