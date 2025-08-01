package com.example.climafy.presentation.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.climafy.presentation.ui.components.FavoriteCityItem
import com.example.climafy.presentation.viewmodel.FavoriteViewModel

@Composable
fun FavoriteScreen(viewModel: FavoriteViewModel = hiltViewModel()) {
    val favoritos by viewModel.favoritos.collectAsState(initial = emptyList())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Cidades Favoritas",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        if (favoritos.isEmpty()) {
            Text("Nenhuma cidade foi adicionada aos favoritos ainda.")
        } else {
            LazyColumn {
                items(favoritos) { cidade ->
                    FavoriteCityItem(
                        cidade = cidade,
                        onDeletar = { viewModel.deletarCidade(cidade) }
                    )
                }
            }
        }
    }
}
