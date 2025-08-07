package com.example.climafy

import com.example.climafy.data.local.entity.FavoriteCityEntity
import com.example.climafy.domain.repository.FavoriteCityRepository
import com.example.climafy.domain.usecase.favorite.ListarCidadesFavoritasUseCase
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ListarCidadesFavoritasUseCaseTest {

    private lateinit var repository: FavoriteCityRepository
    private lateinit var useCase: ListarCidadesFavoritasUseCase

    @Before
    fun setup() {
        repository = mockk()
        useCase = ListarCidadesFavoritasUseCase(repository)
    }

    @Test
    fun `deve retornar lista de cidades favoritas`() = runTest {
        val listaEsperada = listOf(
            FavoriteCityEntity(
                cityName = "Rio de Janeiro",
                country = "BR",
                temperature = 20.0,
                description = "Nublado",
                icon = "03d",
                date = "2023-08-01"
            ),
            FavoriteCityEntity(
                cityName = "São Paulo",
                country = "BR",
                temperature = 15.0,
                description = "Chuvoso",
                icon = "04d",
                date = "2023-08-02"
            )
        )
        val flow = flowOf(listaEsperada)

        every { repository.listarTodas() } returns flowOf(listaEsperada)

        val useCase = ListarCidadesFavoritasUseCase(repository)

        val resultado = useCase().first()

        assertEquals(listaEsperada, resultado)
        verify(exactly = 1) { repository.listarTodas() }
    }
}
