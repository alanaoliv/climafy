import com.example.climafy.presentation.viewmodel.ThemeViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ThemeViewModelTest {

    @Test
    fun `deve alternar o tema corretamente`() = runTest {
        val viewModel = ThemeViewModel()

        assertFalse(viewModel.isDarkTheme.value)

        viewModel.alternarTema()
        assertTrue(viewModel.isDarkTheme.value)
    }
}
