package com.example.climafy.presentation.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.climafy.presentation.viewmodel.WeatherViewModel
import com.example.climafy.ui.theme.ClimafyTheme
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.climafy.domain.model.Weather
import com.example.climafy.presentation.state.WeatherUiState
import com.example.climafy.presentation.ui.components.WeatherCard

@Composable
fun SearchScreen(viewModel: WeatherViewModel = hiltViewModel()) {
    val uiState = viewModel.uiState.value
    var cidade by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "Climafy",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        OutlinedTextField(
            value = cidade,
            onValueChange = { cidade = it },
            label = { Text("Digite o nome da cidade") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = { viewModel.buscarClima(cidade) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Buscar")
        }

        Spacer(modifier = Modifier.height(24.dp))

        when (val state = uiState) {
            is WeatherUiState.Loading -> {
                CircularProgressIndicator(Modifier.align(Alignment.CenterHorizontally))
            }
            is WeatherUiState.Success -> {
                WeatherCard(weather = state.weather)
            }
            is WeatherUiState.Error -> {
                Text("Erro: ${state.message}")
            }
            is WeatherUiState.Empty -> {
                Text("Digite uma cidade e clique em Buscar")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SearchScreenPreview() {
    ClimafyTheme {
        var cidade by remember { mutableStateOf("São Paulo") }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = cidade,
                onValueChange = { cidade = it },
                label = { Text("Digite o nome da cidade") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = {}, modifier = Modifier.fillMaxWidth()) {
                Text("Buscar")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text("Cidade: $cidade")
            Text("Temperatura: 24°C")
            Text("Descrição: Céu limpo")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WeatherCardPreview() {
    ClimafyTheme {
        WeatherCard(
            weather = Weather(
                city = "São Paulo",
                temperature = 24.5,
                description = "céu limpo"
            )
        )
    }
}




