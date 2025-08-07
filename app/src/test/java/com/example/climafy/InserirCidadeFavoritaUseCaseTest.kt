package com.example.climafy

import com.example.climafy.data.local.entity.FavoriteCityEntity
import com.example.climafy.domain.repository.FavoriteCityRepository
import com.example.climafy.domain.usecase.favorite.InserirCidadeFavoritaUseCase
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import io.mockk.just

class InserirCidadeFavoritaUseCaseTest {

    private lateinit var repository: FavoriteCityRepository
    private lateinit var useCase: InserirCidadeFavoritaUseCase

    @Before
    fun setup() {
        repository = mockk(relaxed = true)
        useCase = InserirCidadeFavoritaUseCase(repository)
    }

    @Test
    fun `deve chamar o repository para inserir a cidade`() = runTest {
        val cidade = FavoriteCityEntity(
            cityName = "Curitiba",
            country = "BR",
            temperature = 21.0,
            description = "Nublado",
            icon = "02d",
            date = "2023-08-01",
        )

        coEvery { repository.inserir(cidade) } just Runs

        val useCase = InserirCidadeFavoritaUseCase(repository)

        useCase(cidade)

        coVerify { repository.inserir(cidade) }
    }
}
