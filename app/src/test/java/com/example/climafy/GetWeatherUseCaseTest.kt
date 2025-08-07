package com.example.climafy

import com.example.climafy.domain.model.Weather
import com.example.climafy.domain.repository.WeatherRepository
import com.example.climafy.domain.usecase.GetWeatherUseCase
import io.mockk.coEvery
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest


class GetWeatherUseCaseTest {

    private lateinit var repository: WeatherRepository
    private lateinit var useCase: GetWeatherUseCase

    @Before
    fun setup() {
        repository = mockk()
        useCase = GetWeatherUseCase(repository)
    }

    @Test
    fun `deve retornar clima corretamente quando cidade for válida`() = runTest {
        val cidade = "São Paulo"
        val esperado = Weather(
            city = cidade,
            country = "BR",
            temperature = 25.0,
            description = "Ensolarado",
            icon = "01d",
        )

        coEvery { repository.getWeatherByCity(cidade) } returns esperado

        val resultado = useCase(cidade)

        assertEquals(esperado, resultado)
    }
}
