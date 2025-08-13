package com.example.climafy.presentation.viewmodel

import android.util.Log
import com.example.climafy.MainDispatcherRule
import com.example.climafy.data.local.entity.FavoriteCityEntity
import com.example.climafy.domain.model.Weather
import com.example.climafy.domain.usecase.GetWeatherUseCase
import com.example.climafy.domain.usecase.favorite.DeletarCidadeFavoritaUseCase
import com.example.climafy.domain.usecase.favorite.FavoriteUseCases
import com.example.climafy.domain.usecase.favorite.InserirCidadeFavoritaUseCase
import com.example.climafy.domain.usecase.favorite.ListarCidadesFavoritasUseCase
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class FavoriteViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var listarUC: ListarCidadesFavoritasUseCase
    private lateinit var inserirUC: InserirCidadeFavoritaUseCase
    private lateinit var deletarUC: DeletarCidadeFavoritaUseCase
    private lateinit var getWeatherUC: GetWeatherUseCase

    @Before
    fun setUp() {
        // Evita "Method Log.d/e not mocked" em testes unitários
        mockkStatic(Log::class)
        every { Log.d(any(), any()) } returns 0
        every { Log.e(any(), any(), any()) } returns 0

        listarUC = mockk()
        inserirUC = mockk(relaxed = true)
        deletarUC = mockk(relaxed = true)
        getWeatherUC = mockk()
    }

    private fun createViewModel(
        flowList: List<FavoriteCityEntity> = emptyList()
    ): FavoriteViewModel {
        every { listarUC.invoke() } returns flowOf(flowList)

        val useCases = FavoriteUseCases(
            inserirCidadeFavoritaUseCase = inserirUC,
            listarCidadesFavoritasUseCase = listarUC,
            deletarCidadeFavoritaUseCase = deletarUC
        )

        return FavoriteViewModel(
            favoriteUseCases = useCases,
            getWeatherUseCase = getWeatherUC
        )
    }

    @Test
    fun `deve carregar lista de cidades favoritas`() = runTest {
        val listaFake = listOf(
            FavoriteCityEntity(
                id = 1,
                cityName = "São Paulo",
                country = "BR",
                temperature = 25.0,
                description = "Sol",
                icon = "01d",
                date = "01/01/2025"
            ),
            FavoriteCityEntity(
                id = 2,
                cityName = "Rio de Janeiro",
                country = "BR",
                temperature = 28.0,
                description = "Calor",
                icon = "02d",
                date = "01/01/2025"
            )
        )

        val vm = createViewModel(listaFake)

        // O StateFlow criado com stateIn emite primeiro o valor inicial (emptyList).
        // Por isso usamos drop(1) para pegar a primeira emissão "real".
        val result = vm.favoritos.drop(1).first()

        assertEquals(listaFake, result)
    }

    @Test
    fun `deve chamar useCase para inserir cidade`() = runTest {
        val vm = createViewModel()
        val cidade = FavoriteCityEntity(
            id = 0,
            cityName = "Curitiba",
            country = "BR",
            temperature = 22.0,
            description = "Nublado",
            icon = "02d",
            date = "01/01/2025"
        )

        vm.favoritarCidade(cidade)
        advanceUntilIdle()

        coVerify(exactly = 1) { inserirUC.invoke(cidade) }
        confirmVerified(inserirUC)
    }

    @Test
    fun `deve chamar useCase para deletar cidade`() = runTest {
        val vm = createViewModel()
        val cidade = FavoriteCityEntity(
            id = 10,
            cityName = "Fortaleza",
            country = "BR",
            temperature = 30.0,
            description = "Sol",
            icon = "01d",
            date = "01/01/2025"
        )

        vm.deletarCidade(cidade)
        advanceUntilIdle()

        coVerify(exactly = 1) { deletarUC.invoke(cidade) }
        confirmVerified(deletarUC)
    }

    @Test
    fun `deve atualizar climas favoritos chamando getWeather e salvar atualizados`() = runTest {
        val existentes = listOf(
            FavoriteCityEntity(
                id = 1, cityName = "Recife", country = "BR",
                temperature = 20.0, description = "Antigo", icon = "10d", date = "01/01/2025"
            ),
            FavoriteCityEntity(
                id = 2, cityName = "Natal", country = "BR",
                temperature = 21.0, description = "Antigo", icon = "10d", date = "01/01/2025"
            )
        )
        val vm = createViewModel(existentes)

        coEvery { getWeatherUC("Recife") } returns Weather(
            city = "Recife", country = "BR", temperature = 30.0, description = "Ensolarado", icon = "01d"
        )
        coEvery { getWeatherUC("Natal") } returns Weather(
            city = "Natal", country = "BR", temperature = 27.0, description = "Parcialmente nublado", icon = "02d"
        )

        vm.atualizarClimasFavoritos()
        advanceUntilIdle()

        // Verifica que salvou as versões atualizadas (ignorando a data)
        coVerify {
            inserirUC.invoke(match {
                it.cityName == "Recife" &&
                        it.temperature == 30.0 &&
                        it.description == "Ensolarado" &&
                        it.icon == "01d"
            })
        }
        coVerify {
            inserirUC.invoke(match {
                it.cityName == "Natal" &&
                        it.temperature == 27.0 &&
                        it.description == "Parcialmente nublado" &&
                        it.icon == "02d"
            })
        }
    }
}
