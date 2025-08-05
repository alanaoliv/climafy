package com.example.climafy.presentation.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.climafy.presentation.ui.components.FavoriteCityItem
import com.example.climafy.presentation.viewmodel.FavoriteViewModel
import com.example.climafy.presentation.viewmodel.ThemeViewModel

@Composable
fun FavoriteScreen(
    viewModel: FavoriteViewModel = hiltViewModel(),
    themeViewModel: ThemeViewModel
    ) {
    val favoritos by viewModel.favoritos.collectAsState(initial = emptyList())
    val isDarkTheme by themeViewModel.isDarkTheme.collectAsState()

    Scaffold() { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(paddingValues)
                .padding(bottom = 16.dp)
                .background(MaterialTheme.colorScheme.background)
        ) {
            Text(
                text = "Cidades Favoritas",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 16.dp),
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { themeViewModel.alternarTema() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (isDarkTheme) "Modo Claro" else "Modo Escuro")
            }

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
}
