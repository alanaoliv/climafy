package com.example.climafy.presentation.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.climafy.data.local.entity.FavoriteCityEntity

@Composable
fun FavoriteCityItem(
    cidade: FavoriteCityEntity,
    onDeletar: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Cidade: ${cidade.cityName}, ${cidade.country}", style = MaterialTheme.typography.bodyLarge)
            Text("Temperatura: ${cidade.temperature}°C", style = MaterialTheme.typography.bodyMedium)
            Text("Descrição: ${cidade.description}", style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = onDeletar) {
                Text("Deletar")
            }
        }
    }
}