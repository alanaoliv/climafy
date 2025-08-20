package com.example.climafy.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.climafy.R
import com.example.climafy.domain.model.Weather
import com.example.climafy.presentation.state.WeatherUiState
import com.example.climafy.presentation.viewmodel.WeatherViewModel

@Composable
fun WeatherCard(
    weather: Weather,
    weatherViewModel: WeatherViewModel = hiltViewModel(),
    state: WeatherUiState.Success
) {
    Box(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .height(160.dp)
    ) {
        Card(
            shape = RoundedCornerShape(20.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            colors = CardDefaults.cardColors(containerColor = Color.Transparent),
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .background(
                        brush = Brush.linearGradient(
                            listOf(Color(0xFF6DD5FA), Color(0xFF2980B9))
                        )
                    )
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Coluna da esquerda - Local e descrição
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.Start,
                    ) {
                        Spacer(modifier = Modifier.height(80.dp))
                        Text(
                            text = "${weather.city}, ${weather.country}",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Medium,
                            color = Color.White
                        )
                        Text(
                            text = weather.description,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.White
                        )
                    }

                    // Coluna da direita - Temperatura e min/max
                    Column(
                        horizontalAlignment = Alignment.End,
                        verticalArrangement = Arrangement.Center,
                    ) {
                        Spacer(modifier = Modifier.height(21.dp))
                        Text(
                            text = "${weather.temperature}°",
                            style = MaterialTheme.typography.displayMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )


                        // Max e Min
                        Column(
                            horizontalAlignment = Alignment.End
                        ) {
                            Text(
                                text = "Max: ${weather.tempMax ?: "--"}°",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.White
                            )
                            Text(
                                text = "Min: ${weather.tempMin ?: "--"}°",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }

        IconButton(
            onClick = { weatherViewModel.favoritarCidade(state.weather) },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(6.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = "Favoritar",
                tint = Color.White
            )
        }

        Icon(
            painter = painterResource(id = getWeatherIcon(weather.temperature, weather.description)),
            contentDescription = "Ícone do clima",
            modifier = Modifier
                .size(110.dp)
                .align(Alignment.TopStart)
                .offset(x = 12.dp, y = (-12).dp),
            tint = Color.Unspecified
        )
    }
}
