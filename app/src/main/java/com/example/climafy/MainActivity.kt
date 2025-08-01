package com.example.climafy

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.example.climafy.data.remote.WeatherApiService
import com.example.climafy.data.repository.WeatherRepositoryImpl
import com.example.climafy.domain.usecase.GetWeatherUseCase
import com.example.climafy.presentation.navigation.MainNavigation
import com.example.climafy.presentation.ui.screen.SearchScreen
import com.example.climafy.presentation.viewmodel.WeatherViewModel
import com.example.climafy.ui.theme.ClimafyTheme
import kotlinx.coroutines.launch
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ClimafyTheme {
                val navController = rememberNavController()
                MainNavigation(navController = navController)
            }
        }
    }
}


