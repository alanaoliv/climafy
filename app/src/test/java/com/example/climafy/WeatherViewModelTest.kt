import com.example.climafy.domain.model.Weather
import com.example.climafy.presentation.state.WeatherUiState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class WeatherViewModelTest {

    @Test
    fun `test WeatherUiState Success`() = runTest {

        val fakeWeather = Weather(
            temperature = 25.0,
            description = "Ensolarado",
            city = "São Paulo",
            country = "BR",
            icon = "01d"
        )


        val state = WeatherUiState.Success(fakeWeather)

        assertEquals("São Paulo", state.weather.city)
        assertEquals(25.0, state.weather.temperature, 0.0)
        assertEquals("Ensolarado", state.weather.description)
    }
}
