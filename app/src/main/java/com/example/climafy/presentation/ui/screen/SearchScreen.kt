package com.example.climafy.presentation.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.climafy.presentation.viewmodel.WeatherViewModel
import com.example.climafy.ui.theme.ClimafyTheme
import androidx.hilt.navigation.compose.hiltViewModel

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

        Spacer(modifier = Modifier.height(16.dp))

        when {
            uiState.isLoading -> {
                CircularProgressIndicator()
            }
            uiState.weather != null -> {
                Text("Cidade: ${uiState.weather.name}")
                Text("Temperatura: ${uiState.weather.main.temp}°C")
                Text("Descrição: ${uiState.weather.weather.firstOrNull()?.description}")
            }
            uiState.error != null -> {
                Text("Erro: ${uiState.error}")
            }
        }

    }
}



