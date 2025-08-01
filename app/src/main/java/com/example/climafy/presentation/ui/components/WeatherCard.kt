package com.example.climafy.presentation.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.climafy.domain.model.Weather

@Composable
fun WeatherCard(weather: Weather) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "🌆 ${weather.city}, ${weather.country}",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "🌡️ ${weather.temperature}°C",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            Text(
                text = "☁️ ${weather.description.replaceFirstChar { it.uppercase() }}",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}
