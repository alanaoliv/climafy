package com.example.climafy.presentation.ui.screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.climafy.R
import com.example.climafy.domain.model.Weather
import com.example.climafy.presentation.state.WeatherUiState
import com.example.climafy.presentation.ui.components.FavoriteWeatherCard
import com.example.climafy.presentation.ui.components.WeatherCard
import com.example.climafy.presentation.viewmodel.FavoriteViewModel
import com.example.climafy.presentation.viewmodel.ThemeViewModel
import com.example.climafy.presentation.viewmodel.WeatherViewModel
import com.example.climafy.ui.theme.ClimafyTheme
import kotlinx.coroutines.launch


@Composable
fun SearchScreen(
    weatherViewModel: WeatherViewModel = hiltViewModel(),
    favoriteViewModel: FavoriteViewModel = hiltViewModel(),
    navController: NavHostController,
    themeViewModel: ThemeViewModel
) {
    val uiState = weatherViewModel.uiState.value
    var cidade by remember { mutableStateOf("") }
    val snackbarHostState = remember { SnackbarHostState() }
    val mensagemErro by weatherViewModel.mensagemErro
    val isDarkTheme by themeViewModel.isDarkTheme.collectAsState()
    val favoriteCities by favoriteViewModel.favoritos.collectAsState(emptyList())

    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(mensagemErro) {
        mensagemErro?.let {
            Log.d("SearchScreen", "Mensagem de erro: $it")
            snackbarHostState.showSnackbar(it)
            weatherViewModel.resetarMensagemErro()
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier.padding(16.dp)
            ){ data ->
                Snackbar(
                    snackbarData = data
                )
            }
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(24.dp)
                .padding(paddingValues),
            verticalArrangement = Arrangement.Top
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { themeViewModel.alternarTema() }) {
                    Icon(
                        painter = painterResource(id = if (isDarkTheme) R.drawable.light_mode else R.drawable.dark_mode),
                        contentDescription = if (isDarkTheme) "Modo Claro" else "Modo Escuro",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
                OutlinedTextField(
                    value = cidade,
                    onValueChange = { cidade = it },
                    shape = RoundedCornerShape(16.dp),
                    placeholder = { Text("Digite o nome da cidade") },
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp)
                )
                IconButton(onClick = {
                    if (cidade.isNotBlank()) {
                        weatherViewModel.buscarClima(cidade)
                    } else {
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar("Digite o nome de uma cidade")
                        }
                    }
                }
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Buscar",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }

            }

            Spacer(modifier = Modifier.height(16.dp))

            when (val state = uiState) {
                is WeatherUiState.Loading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 40.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                is WeatherUiState.Success -> {
                    WeatherCard(weather = state.weather, state = state)
                    Spacer(modifier = Modifier.height(8.dp))
                }

                is WeatherUiState.Empty -> {
                    Text("Digite o nome de uma cidade e clique em Buscar")
                }

                else -> Unit

            }
            Spacer(modifier = Modifier.height(24.dp))

            if (favoriteCities.isNotEmpty()) {
                Text(
                    text = "Cidades Favoritas",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )

                TextButton(
                    onClick = { navController.navigate("favorites") },
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text(
                        text = "Ver mais",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    items(favoriteCities) { city ->
                        FavoriteWeatherCard(
                            cityName = city.cityName,
                            temperature = city.temperature,
                            lastUpdate = city.date,
                            description = city.description
                        )
                    }
                }
            }
        }
    }
}
